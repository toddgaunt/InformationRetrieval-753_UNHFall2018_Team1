# This is simply a wrapper around mvn to aid muscle memory for make commands

QUESTION := "Q1"
PARA := "fold-0-train.pages.cbor-paragraphs.cbor"
OUTLINE := "train.pages.cbor-outlines.cbor"

all:
	mvn compile

clean:
	mvn clean
	rm -rf index/
	rm -f default.runfile custom.runfile

run: all
	rm -f default.runfile custom.runfile
	rm -rf index/
	mvn exec:java -Dexec.mainClass="cs753.T1.A5.App" -Dexec.args="$(QUESTION) $(PARA) $(OUTLINE) $(RETRIEVAL)"
