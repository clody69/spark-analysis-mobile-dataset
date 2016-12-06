# spark-analysis-mobile-dataset
This an experiment for analyzing a large mobile dataset with Spark.

# Setup and quick start

To run this experiment you need to install Spark and Java. The best way to install it on Mac:

	brew install apache-spark

Next, we have to download and unzip the dataset:

	curl -o tmp.zip http://www.mathesia.com/projects/wp-content/uploads/2016/11/myd-cleaned-translated.json_.zip && unzip tmp.zip && rm tmp.zip && mv myd-cleaned-translated.json data.json

The file `data.json` contains the full dataset to analyze. In order to run the analysis, just execute the following command:

	spark-shell	-i run.scala

# The experiment

## Objectives
The objective is to extract the most relevant topics for the users by analyzing the logs of the dataset. The solution should identify the micro interests of the users and work in real-time.

## Analysis of the dataset
The first step is to analyze the dataset and decide what information is most suitable or needs some corrections. Using Spark you can load the JSON file and automatically infer the underlying schema:

	var df = spark.read.json("test.json")
	df.printSchema

The schema is shown in the file [schema.md](schema.md) and it shows the dataset consists of several records of structured data. 

The dataset consists of 77 records each containing the following data:
* *_id_*: identifier of the user
* *bgGeos*: GPS logs containing latitude, longitude and other info collected for a period of 30 days. 
* *checkins*: a list of location based checkins with a list of categories and timestamp
* *likes*: a list of facebook likes with name and category but not timestamp
* *urls*: a list of visited urls with names, inferred categories and relevance and no timestamp

Using the Spark query api we can extract some basic statistics about the daset, for instance for the likes:
	
	scala> df.select(explode($"likes.id"),$"_id").groupBy("_id").count.describe().show

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
