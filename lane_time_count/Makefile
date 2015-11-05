all: ltc.jar
clean:
	rm -rf *.class *.jar

ltc.jar: LaneTimeCount.class
	jar cf ltc.jar *.class

LaneTimeCount.class: LaneTimeCount.java
	javac -classpath /usr/local/hadoop/share/hadoop/common/hadoop-common-2.7.1.jar:/usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar *.java
red: ltc.jar
	hadoop fs -rm -r -f output/vehicles_small/
	hadoop jar ltc.jar LaneTimeCount input/vehicles_small/ output/vehicles_small/
	hadoop fs -cat output/vehicles_small/part-r-00000
map: ltc.jar
	hadoop fs -rm -r -f output/vehicles_small/
	hadoop jar ltc.jar LaneTimeCount input/vehicles_small/ output/vehicles_small/
	hadoop fs -cat output/vehicles_small/part-m-00000
run: ltc.jar
	hadoop fs -rm -r -f output/vehicles/
	hadoop jar ltc.jar LaneTimeCount input/vehicles/ output/vehicles/
	#hadoop fs -cat output/vehicles/part-m-00000
