#!/bin/sh

LUCENE_VERSION=7.4.0
LUCENE_MIRROR=http://apache.claz.org/lucene/java/$LUCENE_VERSION/lucene-$LUCENE_VERSION.tgz

mkdir -p lib
cd lib/
wget -nc "$LUCENE_MIRROR" .
tar -xzvf lucene-$LUCENE_VERSION.tgz
