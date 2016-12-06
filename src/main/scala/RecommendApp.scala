import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{StructType, StructField, LongType, IntegerType}
import org.apache.spark.sql.functions._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Dataset

import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.recommendation.ALS

import org.apache.spark.ml.feature.RegexTokenizer
import org.apache.spark.ml.feature.HashingTF
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.classification.NaiveBayes
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.tuning.ParamGridBuilder
import org.apache.spark.ml.tuning.CrossValidator
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator

case class Recommendation (name: String, category: String, rating: Double)

object RecommendApp {
  def main(args: Array[String]) {

		val spark = SparkSession
		   .builder()
		   .appName("Recommendation Engine")
		   .getOrCreate()

		import spark.implicits._
		// *** Load the dataset ***

		spark.sparkContext.setLogLevel("ERROR")

		println("Importing dataset")

		// Load the raw dataset into a Spark dataframe
		var df = spark.read.json("test.json")

		// Rename the user id column to avoid clashes with other ids
		var inputDF = df.withColumnRenamed("_id","user")

		// Collect all users ids
		var users = inputDF.select("user").map(_.getString(0)).collect


		// *** Exploding the datasaet for easier access ***

		//Explode Likes
		var userLike = inputDF.select($"user", explode($"likes").alias("likes")).select($"user", $"likes.*").cache()

		var userCheckin = inputDF.select($"user", explode($"checkins").alias("checkins")).select($"user", $"checkins.*").cache()

		var userUrl = inputDF.select($"user", explode($"urls").alias("urls")).select($"user", $"urls.*").cache()



		// *** Category Classifier for checkin documents

		println("Classifying categories")

		// Add the `text` column that will contain the words for training the classifier
		var likeCategories = userLike.select("category").distinct.select($"category", $"category".alias("text"))

		// Additional mapping to make the classifier more robust
		var additionalCategories = Seq( 
		        ("Political Party","political"), 
		        ("Transportation", "subway railway train transit"),
		        ("Home Decor", "home"),("Shopping/Retail", "shoe store shopping mall"),
		        ("Travel/Leisure", "lodging"),
		        ("Food", "bakery") )
		    .toDF("category","text")

		// Full dataset that maps categories with words (respiecivelly in the `category` and `text` columns)
		var categories = likeCategories.union(additionalCategories).cache()

		// Map category to Integer
		val categoryIndexer = new StringIndexer()
		    .setInputCol("category")
		    .setOutputCol("categoryIndex")
		    .fit(categories)

		// Break down the `text` column using space, underscore and slash
		val tokenizer = new RegexTokenizer()
		    .setInputCol("text")
		    .setOutputCol("words")
		    .setPattern("\\s+|_|/")

		val hashingTF = new HashingTF()
		    .setInputCol(tokenizer.getOutputCol)
		    .setOutputCol("features")
		    .setNumFeatures(5000)

		// Input column for the bayes classifier. Output column will be the default `prediction`
		var nb = new NaiveBayes()
		    .setLabelCol("categoryIndex")

		// Convert the `prediction` from integer id to category label
		var categoryConverter = new IndexToString()
		    .setInputCol("prediction")
		    .setOutputCol("predCategory")
		    .setLabels(categoryIndexer.labels)

		// Set up the pipeline
		val category_pipeline = new Pipeline()
		    .setStages(Array(categoryIndexer, tokenizer, hashingTF, nb, categoryConverter))

		// Train the classifier
		val categoryModel = category_pipeline.fit(categories)
		// categoryModel.transform(Seq("Bar travel fun", "Travel website","spas","bar","sports","beauty").toDF("text")).show

		// Take the `types` column and create the `text` column that contains the words to be used for the classification
		val mkString = udf( (arg:Seq[Any]) => { arg.filter(e => e !="point_of_interest").filter(e => e !="locality").filter(e => e !="establishment").mkString(" ")} )
		var checkins = userCheckin.withColumn("text", when($"types"isNull, "").otherwise(mkString($"types"))).cache()

		// Classify the recrods and save them
		var corrected_checkins = categoryModel.transform(checkins)


		// ****** recommendation pipeline *****
		// *** Create user interests dataset

		println("Creating ALS pipeline")

		var user_interests_like = userLike.withColumn("rating",lit(1))
		var user_interests_checkin = corrected_checkins.groupBy("user","predCategory","_id","name").count.withColumnRenamed("count","rating")
		var user_interests = user_interests_like.union(user_interests_checkin)

		// Create the string indexers for users and products
		val userIndexer = new StringIndexer()
		    .setInputCol("user")
		    .setOutputCol("userID")
		    .fit(user_interests)

		val itemIndexer = new StringIndexer()
		    .setInputCol("id")
		    .setOutputCol("itemID")
		    .fit(user_interests)

		// *** calculate the ALS parameters

		def calculate_ALS_parameters(): (Integer, Integer, Double) = {

		    val ranks = List(4, 8, 12)
		    val lambdas = List(0.01, 0.1, 1.0)
		    val numIters = List(10, 20)
		    var bestValidationRmse = Double.MaxValue
		    var bestRank = 0
		    var bestLambda = -1.0
		    var bestNumIter = -1

		    val Array(training, test) = user_interests.randomSplit(Array(0.8, 0.2))
		    var reduced_test = test.join(training.select($"id".alias("ext_id")).distinct, $"id" === $"ext_id")

		    println("Size for training dataset: " + training.count)
		    println("Size for test dataset: " + reduced_test.count)

		    val evaluator = new RegressionEvaluator().setMetricName("rmse").setLabelCol("rating").setPredictionCol("prediction")

		    for (rank <- ranks; lambda <- lambdas; numIter <- numIters) {

		        var test_als = new ALS().setRank(rank).setMaxIter(numIter).setRegParam(lambda).setImplicitPrefs(true).setUserCol("userID").setItemCol("itemID").setRatingCol("rating")

		        val test_pipeline = new Pipeline().setStages(Array(userIndexer, itemIndexer, test_als))

		        val testModel = test_pipeline.fit(training)
		        var predictions = testModel.transform(reduced_test)

		        val validationRmse = evaluator.evaluate(predictions)

		        println("RMSE (validation) = " + validationRmse + " for the model trained with rank = "
		        + rank + ", lambda = " + lambda + ", and numIter = " + numIter + ".")

		        if (validationRmse < bestValidationRmse) {
		            //bestModel = Some(model)
		            bestValidationRmse = validationRmse
		            bestRank = rank
		            bestLambda = lambda
		            bestNumIter = numIter
		            println("Found best parameters with rmse:" + validationRmse )
		        }
		    }

		    println("The best model was trained with rank = " + bestRank + " and lambda = " + bestLambda
		      + ", and numIter = " + bestNumIter + ", and its RMSE on the test set is " + bestValidationRmse + ".")

		    (bestRank, bestNumIter, bestLambda)
		}


		println("Calculating optimal ALS parameters")
		// Calculate optimal parameters
		// Uncomment lines below to calculate the optimal parameters
		//val (rank, maxIter, regParam) = calculate_ALS_parameters()

		// Optimal values (previously calculated):
		val (rank, maxIter, regParam) = (4, 20, 0.01)

		println("Using ALS parameters: rank="+rank+" maxIter="+ maxIter + " regParam=" + regParam)


		// *** Create the ALS pipeline

		var als = new ALS()
			.setRank(rank)
			.setMaxIter(maxIter)
			.setRegParam(regParam)
			.setImplicitPrefs(true)
			.setUserCol("userID")
			.setItemCol("itemID")
			.setRatingCol("rating")


		val recommend_pipeline = new Pipeline().setStages(Array(userIndexer, itemIndexer, als))
		val recommendModel = recommend_pipeline.fit(user_interests)

		// Calculate recommendations for random user


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

		println("Predicting for random user")

		// Calculate the recommendations for a randome user
		predict_random_user()


		//Calculate most popular checkins in last 2 days
		println("Most popular chekcins in the last 2 days")
		userCheckin.withColumn("timestamp",$"date".getItem("$date")).groupBy(window($"timestamp","2 days"),$"name").count.filter($"count">1).orderBy(desc("window")).show(30, false)

		//How many users have already been there in the last month
		println("Number of checkins in the Travel/Leisure category during the last month")
		corrected_checkins.withColumn("timestamp",$"date".getItem("$date")).groupBy(window($"timestamp","30 days"),$"predCategory").count.filter($"count">1).filter($"predCategory" contains "Travel/Leisure").orderBy(desc("window")).show(30, false)

	}
}
