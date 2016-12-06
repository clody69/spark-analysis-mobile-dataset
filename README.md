# spark-analysis-mobile-dataset
This an experiment for analyzing a large mobile dataset with Spark.

# Setup and quick start

To run this experiment you need to install Spark and Java. The best way to install it on Mac:
```bash
	brew install apache-spark
	brew install sbt
```
Next, you have to download and unzip the dataset:
```bash
	curl -o tmp.zip http://www.mathesia.com/projects/wp-content/uploads/2016/11/myd-cleaned-translated.json_.zip && unzip tmp.zip && rm tmp.zip && mv myd-cleaned-translated.json data.json
```
The file `data.json` contains the full dataset to analyze. In order to run the analysis, just execute the following commands:
```bash
	sbt package
	spark-submit --class "RecommendApp" --master local[4] target/scala-2.11/recommend-app_2.11-1.0.jar
```
Some of the code below can be executed by copy pasting into the Spark shell (use the command :paste to paste multiline). To start the shell:
```bash
	spark-shell
```
# The experiment

## Objectives
The objective is to extract the most relevant topics for the users by analyzing the logs of the dataset. The solution should identify the micro interests of the users and work in real-time.

## Analysis of the dataset
The first step is to analyze the dataset and decide what information is most suitable or needs some corrections. Using Spark you can load the JSON file and automatically infer the underlying schema:
```scala
	var df = spark.read.json("test.json")
	df.printSchema
```
The schema is shown in the file [schema.md](schema.md) and it shows the dataset consists of several records of structured data. 

The dataset consists of 77 records each containing the following data:
* *_id_*: identifier of the user
* *bgGeos*: GPS logs containing latitude, longitude and other info collected for a period of 30 days. 
* *checkins*: a list of location based checkins with a list of categories and timestamp
* *likes*: a list of facebook likes with name and category but not timestamp
* *urls*: a list of visited urls with names, inferred categories and relevance and no timestamp

Using the Spark query api we can extract some basic statistics about the daset, for instance for the likes:
```scala	
	df.select(explode($"likes.id"),$"_id").groupBy("_id").count.describe().show
```
The result for all the 4 categories of data:

	+-------+-----------------+ +------------------+ +------------------+ +------------------+
	|summary|            likes| |          checkins| |              urls| |            bgGeos|
	+-------+-----------------+ +------------------+ +------------------+ +------------------+
	|  count|               73| |                72| |                62| |                77|
	|   mean|455.6575342465753| |19.944444444444443| | 13.35483870967742| |1106.5844155844156|
	| stddev|550.4841863501744| |19.197189413483212| |13.213377193541584| |2957.9922438185613|
	|    min|                2| |                 1| |                 1| |                 1|
	|    max|             3748| |               123| |                58| |             22934|
	+-------+-----------------+ +------------------+ +------------------+ +------------------+

The GPS logs (`bgGeos`) is the biggest dataset. Although it contains very valuable information about the movements of the users, it doesn't contain information that can quickly answer our objective. Whereas `likes`, `checkins` and `urls` are more informative for our objective, so we focus on those. 

A quick look at the categories of the 3 shows that:
* likes categories are taken from the [standard Facebook list of categories](https://www.facebook.com/pages/create.php) 
* checkins categories are taken from the [Google Places Categories](http://blumenthals.com/google-lbc-categories/search.php?q=&val=hl-gl%3Den-US%28PfB%29%26ottype%3D1)
* urls contain a list of categories and concepts that are not mapped to a standard list.

Because  the `likes` dataset is the largest, I decided to use the Facebook categories as a reference. The categories in the `checkins` dataset can be mapped to the facebook dataset. The categories of the `urls` require to be translated and require more work for the mapping, so I won't consider the `urls` in this experiment. The full list of categories is located at [categories.md](categories.md) and has been extracted with the following queries:
```scala
	df.select(explode($"likes.category").alias("category")).groupBy("category").count.orderBy(desc("count")).show(10000,false)
	df.select(explode($"checkins.types").alias("category")).groupBy("category").count.orderBy(desc("count")).show(10000,false)
	df.select(explode($"urls.categories").alias("categories")).select(explode($"categories.label").alias("category")).groupBy("category").count.orderBy(desc("count")).show(1000,false)
```
## Cleaning the datasets

Raw datasets often require to be cleaned before they are suitable for a further analysis. In our case we need do to the following:
* Exploding the nested fields. We must explode some of the nested fields for easy access.
* * Consistent categories. As we'll use the `likes` categorires as a reference we must map the `checkins` categories.

### Explode the nested datasets

The dataset contains structured data that we explode in order to simplify the access. We explode the `likes`, `checkins` and `urls` and we cache the resulting datasets.
```scala
	var userLike = inputDF.select($"userID", explode($"likes").alias("likes")).select($"userID", $"likes.*").cache()

	var userCheckin = inputDF.select($"userID", explode($"checkins").alias("checkins")).select($"userID", $"checkins.*").cache()

	var userUrl = inputDF.select($"userID", explode($"urls").alias("urls")).select($"userID", $"urls.*").cache()
```

### Mapping the categories

There are several ways how we could map the `checkin` types to the `likes` categories. To achieve a precise mapping, the best way would be to create a 1:1 mapping among the categories. As this would be rather time consuming and it's out of the scope of this exercise, we decided to create naive Bayesian classifier that does the mapping for us. First we train the filter with the `likes` categories and the words extracted by breaking down the category names into tokens (using the `spaces` and `/` as delimiters). Then we feed the classifier with the `checkin` types (tokenizing them also using `_`) in order to classify all the `checkin` records with the `likes` categories.  

As an example the category `Spas/Beauty/Personal Care` is tokeninzed with the words `Spa`, `Beauty`, `Personal` and `Care`. Documents containting those words are likely to me mapped to the `Spas/Beatuy/Persnal Care` category. For instance, the records from `checkin` that contains this record `[beauty_salon, spa, point_of_interest, establishment]` will be mapped to `Spas/Beatuy/Persnal Care`.

To make the classifier more robust we ignore the keywords `point_of_interest` and `establishment` as they are very common and we add some additional words for a few categories. 

Below there is the mapping from types to categories for 20 records of the `checkin` dataset. Clearly there is space for improvement for refining the mappings, however this is good enough for our experiment.

	+-------------------------------------------------------------------+---------------+
	|types                                                              |predCategory   |
	+-------------------------------------------------------------------+---------------+
	|[locality, political]                                              |Political Party|
	|[restaurant, bar, food, point_of_interest, establishment]          |Food           |
	|[bakery, store, food, point_of_interest, establishment]            |Food           |
	|[point_of_interest, establishment]                                 |Political Party|
	|[store, point_of_interest, establishment]                          |Shopping/Retail|
	|[store, point_of_interest, establishment]                          |Shopping/Retail|
	|[restaurant, bar, food, point_of_interest, establishment]          |Food           |
	|[store, point_of_interest, establishment]                          |Shopping/Retail|
	|[book_store, store, point_of_interest, establishment]              |Book Store     |
	|[bakery, restaurant, food, store, point_of_interest, establishment]|Food           |
	|[restaurant, bar, food, point_of_interest, establishment]          |Food           |
	|[restaurant, bar, food, point_of_interest, establishment]          |Food           |
	|[point_of_interest, establishment]                                 |Political Party|
	|[home_goods_store, store, point_of_interest, establishment]        |Shopping/Retail|
	|[locality, political]                                              |Political Party|
	|[restaurant, food, point_of_interest, establishment]               |Food           |
	|[locality, political]                                              |Political Party|
	|[post_office, finance, point_of_interest, establishment]           |Office Supplies|
	|[bar, restaurant, food, point_of_interest, establishment]          |Food           |
	|[locality, political]                                              |Political Party|
	+-------------------------------------------------------------------+---------------+


## Recommendation pipeline
We are now ready to prepare the recommendation pipeline. A common technique is the collaborative filtering that allows us to make predictions about the interests of a user based on the preferences of many users. The basic idea is that if a user A has the same opinion on a product as another user B, than A is more likely to have the same opinion of B on a different product. In our case we can use the `likes` and `checkins` as the opinions of the users on the various products that in our case are the objects that are liked or checked in. Since we don't have a concrete list of real products we consider the liked/checkedin objects as our products.

The Spark Mlib library provides an implementation of the collaborative filtering that is based on the [Alternating Least Squares](http://dl.acm.org/citation.cfm?id=1608614). We are going to use this implemenation in this experiment. 

### Create the ratings 

The ALS algorithms expects as input a list of records in the format `(user, product, rating)`. In our case we don't have explict ratings from the users but we must infer the ratings from the user behaviour. As a starting point for calculating the rating I followed this model:
* a like counts as 1 in the rating
* the number of checkins become the rating

It's a rather primitive approach that would require validation and tuning, however it's good enough for our exercise.

The column ratings is created with the following code:
```scala
	var user_interests_like = userLike.withColumn("rating",lit(1))
	var user_interests_checkin = corrected_checkins.groupBy("userID","predCategory","_id","name").count.withColumnRenamed("count","rating")
	var user_interests = user_interests_like.union(user_interests_checkin)
```
The dataset `user_interests` contains the user/product/rating information that we need for the ALS pipeline. 

### The ALS pipeline
Setting up the ALS pipeline requires to convert the user and product ids into integers and select some parameters. The selection of the parameters is explained in the section below.
We are using the new pipeline construct that is available on Spark 2.0. We create 2 indexers for converting the strings to ids and the actual ALS.
```scala
	val userIndexer = new StringIndexer()
	    .setInputCol("user")
	    .setOutputCol("userID")
	    .fit(user_interests)

	val itemIndexer = new StringIndexer()
	    .setInputCol("id")
	    .setOutputCol("itemID")
	    .fit(user_interests)

	var als = new ALS()
		.setMaxIter(5)
		.setRegParam(0.01)
		.setImplicitPrefs(true)
		.setUserCol("user")
		.setItemCol("itemID")
		.setRatingCol("rating")

	val recommend_pipeline = new Pipeline().setStages(Array(userIndexer, itemIndexer, als))
	val recommendModel = recommend_pipeline.fit(user_interests)
```
The object `recommendModel` now is ready to be used for predicting the users preferences. 

### Selecting the ALS parameters

To select the best parameters for ALS we split the dataset into training and test dataset. First we train the ALS with the training data and then we predict the ratings for the test data. Knowing the ratings of the test users we can calculate the minimum square error (MSE) of the predictions. We run the ALS algorithm for several combinations of the parameters and we pick the configuration that is minimizing the MSE. The result gave the following configuration: rank = 4, maxIters = 20, regParam = 0.01

### Top recommendations for the user

To predict the top recommendations for the user we do the following:

1. Calculate the items that have not been liked or checked in by a particular user
2. Use the ALS pipeline to predict the ratings of the products for the particular user
3. Adjust the predictions using the user's top categories
4. Print the top 20 recommendations

The step 3 allows to increase the ratings of the predictions based on the categories where the user had the highest number of likes/checkins. This is a naive interpreation of the algorithm that has been used by Google to create [personalized news recommendations](http://static.googleusercontent.com/media/research.google.com/en//pubs/archive/35599.pdf) for Google News.

This algorithm is deliverying predictions independently of the categories that are trending in a particular moment. The algorithm could be further tuned by taking the trends into account (using the trends calculated below). This is not included in this algorithm yet. 

Here's the code for the function that calculates the predictions
```scala
	case class Recommendation (name: String, category: String, rating: Double)

	def predict_random_user() :  Dataset[Recommendation] = {
	    var random_user = util.Random.shuffle(users.toSeq).take(1)(0)

	    var random_user_interests = user_interests.filter($"user" === random_user)
	    var max_count = random_user_interests.groupBy("category").count.select(max($"count")).map(x=>x.getLong(0)).head
	    var category_relevance = random_user_interests.groupBy("category").count.select($"category".alias("cat_relevance"), ($"count"/max_count+1).alias("relevance"))

	    var random_user_unrated = user_interests.filter($"user" === random_user).select("category","id","name").distinct.withColumn("user", lit(random_user))
	    var predictions = recommendModel.transform(random_user_unrated)

	    var recommendations = predictions
	        .join(category_relevance, category_relevance("cat_relevance") === predictions("category"),"left")
	        .withColumn("rating",when($"relevance"isNull, $"prediction").otherwise($"prediction"*$"relevance"))
	        .select($"name", $"category", $"rating")
	        .as[Recommendation]

	    println("Predictions for user:" + random_user)
	    println("User's top 20 categories:")
	    userLike.filter($"user" === random_user).groupBy("category").count.orderBy(desc("count")).show(false)    
	    println("User's likes:")
	    userLike.filter($"user" === random_user).show(false)    

	    println("User's top 20 recommendations:")
	    recommendations.orderBy(desc("rating")).show(false)

	    return recommendations
	}

```
### Examples 
Some examples of recommendations for random users are available in the file [recommendations.md](recommendations.md). 


### New users
New users will have to be added to the recommendation engine and the ALS will have to be recalculated. The best is to run this operation in batches or using the Spark streaming capability.

## Trending topics

With spark we can also easily calculate the trending topics given a particular time period. Below several examples:

### Most popular checkins in the last 2 days
The table below shows the items that have been checked in by at least 2 users in a given time window of 2 days.

	scala> userCheckin.withColumn("timestamp",$"date".getItem("$date")).groupBy(window($"timestamp","2 days"),$"name").count.filter($"count">1).orderBy(desc("window")).show(30, false)
	+---------------------------------------------+--------------------------------+-----+
	|window                                       |name                            |count|
	+---------------------------------------------+--------------------------------+-----+
	|[2016-08-15 02:00:00.0,2016-08-17 02:00:00.0]|Bar Gelateria Venturelli William|2    |
	|[2016-08-05 02:00:00.0,2016-08-07 02:00:00.0]|Naples                          |2    |
	|[2016-08-05 02:00:00.0,2016-08-07 02:00:00.0]|Milano Centrale Railway Station |2    |
	|[2016-08-01 02:00:00.0,2016-08-03 02:00:00.0]|Al Mercatino tra Noi e Voi      |2    |
	|[2016-07-30 02:00:00.0,2016-08-01 02:00:00.0]|Sexy Shop Online - SexYouShop.it|2    |
	|[2016-07-30 02:00:00.0,2016-08-01 02:00:00.0]|Cesano Boscone                  |2    |
	|[2016-07-30 02:00:00.0,2016-08-01 02:00:00.0]|Trattoria Caprese               |3    |
	|[2016-07-30 02:00:00.0,2016-08-01 02:00:00.0]|Parco Nord Milano               |2    |
	|[2016-07-28 02:00:00.0,2016-07-30 02:00:00.0]|Cesano Boscone                  |5    |
	|[2016-07-28 02:00:00.0,2016-07-30 02:00:00.0]|Milan                           |2    |
	|[2016-07-28 02:00:00.0,2016-07-30 02:00:00.0]|Trezzano sul Naviglio           |2    |
	|[2016-07-26 02:00:00.0,2016-07-28 02:00:00.0]|Milan                           |9    |
	|[2016-07-26 02:00:00.0,2016-07-28 02:00:00.0]|Esselunga                       |3    |
	|[2016-07-26 02:00:00.0,2016-07-28 02:00:00.0]|Termoli                         |2    |
	|[2016-07-26 02:00:00.0,2016-07-28 02:00:00.0]|Garibaldi-Isola                 |2    |
	|[2016-07-26 02:00:00.0,2016-07-28 02:00:00.0]|Prénatal                        |2    |
	|[2016-07-26 02:00:00.0,2016-07-28 02:00:00.0]|Cesano Boscone                  |3    |
	|[2016-07-24 02:00:00.0,2016-07-26 02:00:00.0]|Cesano Boscone                  |2    |
	|[2016-07-24 02:00:00.0,2016-07-26 02:00:00.0]|Milan                           |2    |
	|[2016-07-22 02:00:00.0,2016-07-24 02:00:00.0]|Cinisello Balsamo               |2    |
	|[2016-07-22 02:00:00.0,2016-07-24 02:00:00.0]|Milan                           |7    |
	|[2016-07-20 02:00:00.0,2016-07-22 02:00:00.0]|Porta Venezia                   |3    |
	|[2016-07-20 02:00:00.0,2016-07-22 02:00:00.0]|Zona Buenos Aires               |2    |
	|[2016-07-20 02:00:00.0,2016-07-22 02:00:00.0]|Milan                           |3    |
	|[2016-07-18 02:00:00.0,2016-07-20 02:00:00.0]|Milan                           |5    |
	|[2016-07-18 02:00:00.0,2016-07-20 02:00:00.0]|Garibaldi-Isola                 |2    |
	|[2016-07-18 02:00:00.0,2016-07-20 02:00:00.0]|Luisa Spagnoli                  |2    |
	|[2016-07-18 02:00:00.0,2016-07-20 02:00:00.0]|Porta Venezia                   |2    |
	|[2016-07-18 02:00:00.0,2016-07-20 02:00:00.0]|Bellagio                        |2    |
	|[2016-07-16 02:00:00.0,2016-07-18 02:00:00.0]|Parrocchia S. Giovanni Battista |2    |
	+---------------------------------------------+--------------------------------+-----+
	only showing top 30 rows


### How many users have been in vacation in the last month

The following query calculates how many users have checked in a location that is labelled as 'Travel/Leisure' grouped per month

	scala> corrected_checkins.withColumn("timestamp",$"date".getItem("$date")).groupBy(window($"timestamp","30 days"),$"predCategory").count.filter($"count">1).filter($"predCategory" contains "Travel/Leisure").orderBy(desc("window")).show(30, false)
	+---------------------------------------------+--------------+-----+
	|window                                       |predCategory  |count|
	+---------------------------------------------+--------------+-----+
	|[2016-07-28 02:00:00.0,2016-08-27 02:00:00.0]|Travel/Leisure|9    |
	|[2016-06-28 02:00:00.0,2016-07-28 02:00:00.0]|Travel/Leisure|41   |
	|[2016-05-29 02:00:00.0,2016-06-28 02:00:00.0]|Travel/Leisure|2    |
	+---------------------------------------------+--------------+-----+

## Conclusions

We have built a simple recommendation pipeline for suggesting products to a group of users based on their behaviour (pages they liked and places they have checked in). The pipeline required to adjust some of the input data and create an homogeneus set of categories (we relied on a naive Bayes classifer for this task). 
The outcome is a list of recommendations that should be validated. Consider that in this experiment we didn't have a precise set of products to recommend but we simply took the objects that have been liked by the users. A further mapping would be required in case of real products. 
In addition we have not included other factors like location, visited urls, daily habits etc that could strenghen the recommendations.
The Spark pipeline has been designed for batch processessing but it could be easily turned into a streaming pipeline with minimal code changes. This would allow to process and produce recommendations in real-time.





