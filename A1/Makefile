# This is simply a wrapper around mvn to aid muscle memory for make commands
INPUT := "fold-0-train.pages.cbor-paragraphs.cbor"

all:
	mvn compile

clean:
	mvn clean
	rm -rf index/

run: all
	rm -rf index/
	mvn exec:java -Dexec.mainClass="T1753A1.App" -Dexec.args="$(INPUT)"

run-windows: all
	rm -rf index/
	mvn exec:java -D"exec.mainClass"="T1753A1.App"
