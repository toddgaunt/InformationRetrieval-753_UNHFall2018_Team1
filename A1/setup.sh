# Script to run when first setting this maven project on a new computer

mvn install:install-file -Dfile=../lib/lucene-7.4.0/core/lucene-core-7.4.0.jar -DgroupId=org.apache.lucene -DartifactId=lucene -Dversion=7.4.0 -Dpackaging=jar
