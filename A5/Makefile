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
	mvn exec:java -Dexec.mainClass="cs753.T1.A5.App" -Dexec.args="$(PARA) $(OUTLINE)"

Q1: all
	rm -f rankLibQ1.train
	mvn exec:java -Dexec.mainClass="cs753.T1.A5.Q1"
	java -jar RankLib-2.10.jar -train rankLibQ1.train -metric2t MAP | grep "MAP on"

Q2: all
	rm -f rankLibQ2.train
	mvn exec:java -Dexec.mainClass="cs753.T1.A5.App" -Dexec.args="$(PARA) $(OUTLINE)"
	java -jar RankLib-2.10.jar -train rankLibQ2.train -qrel train.pages.cbor-article.qrels -metric2t MAP | grep "MAP"