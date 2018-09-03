#!/bin/sh


# Download and unpack lucene dependency 

LUCENE_VERSION=7.4.0
LUCENE_MIRROR=http://apache.claz.org/lucene/java/$LUCENE_VERSION/lucene-$LUCENE_VERSION.tgz

if [ ! -e lib/lucene.flag ]; then
	mkdir -p lib
	cd lib/
	wget -nc "$LUCENE_MIRROR" .
	tar -xzvf lucene-$LUCENE_VERSION.tgz
	touch lucene.flag
fi

# Download and compile the TREC parser

if [ ! -e lib/trec.flag ]; then
	mkdir -p lib
	cd lib/
	git clone https://github.com/TREMA-UNH/trec-car-tools-java.git
	cd trec-car-tools-java/
	mvn package
	touch trec.flag
fi
