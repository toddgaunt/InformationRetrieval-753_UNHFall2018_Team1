# This is simply a wrapper around mvn to aid muscle memory for make commands

all:
	mvn compile

clean:
	mvn clean

run: all
	rm -rf index/
	mvn exec:java -Dexec.mainClass="T1753A1.App"

run-windows: all
	rm -rf index/
	mvn exec:java -D"exec.mainClass"="T1753A1.App"
