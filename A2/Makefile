# This is simply a wrapper around mvn to aid muscle memory for make commands

ARG := "train.pages.cbor-outlines.cbor"

all:
	mvn compile

clean:
	mvn clean

run: all
	rm -rf index/
	mvn exec:java -Dexec.mainClass="cs753.T1.A2.App" -Dexec.args="$(ARG)"

run-windows: all
	rm -rf index/
	mvn exec:java -D"exec.mainClass"="cs753.T1.A2.App" -Dexec.args="$(ARG)"
