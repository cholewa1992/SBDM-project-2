all: tc.jar
clean:
	rm -rf *.class *.jar

tc.jar: TimeCount.class
	jar cf tc.jar *.class

TimeCount.class: TimeCount.java
	javac -classpath /usr/local/hadoop/share/hadoop/common/hadoop-common-2.7.1.jar:/usr/local/hadoop/share/hadoop/mapreduce/hadoop-mapreduce-client-core-2.7.1.jar *.java
red: tc.jar
	hadoop fs -rm -r -f output/vehicles_small/
	hadoop jar tc.jar TimeCount input/vehicles_small/ output/vehicles_small/
	hadoop fs -cat output/vehicles_small/part-r-00000
map: tc.jar
	hadoop fs -rm -r -f output/vehicles_small/
	hadoop jar tc.jar TimeCount input/vehicles_small/ output/vehicles_small/
	hadoop fs -cat output/vehicles_small/part-m-00000
run: tc.jar
	hadoop fs -rm -r -f output/vehicles/
	hadoop jar tc.jar TimeCount input/vehicles/ output/vehicles/
	hadoop fs -cat output/vehicles/part-m-00000
