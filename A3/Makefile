# This is simply a wrapper around mvn to aid muscle memory for make commands

PARA := "fold-0-train.pages.cbor-paragraphs.cbor"
OUTLINE := "train.pages.cbor-outlines.cbor"
RETRIEVAL := "bnn.bnn"

all:
	mvn compile

clean:
	mvn clean
	rm -rf index/
	rm -f default.runfile custom.runfile

run: all
	rm -f default.runfile custom.runfile
	rm -rf index/
	mvn exec:java -Dexec.mainClass="cs753.T1.A3.App" -Dexec.args="$(PARA) $(OUTLINE) $(RETRIEVAL)"
