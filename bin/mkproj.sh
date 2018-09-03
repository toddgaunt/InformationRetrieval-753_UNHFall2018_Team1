#!/bin/sh

NAME=$1

if [[ "$1" == "" ]]; then
	echo "mkproj <project-name>"
	exit 1 
fi

mvn -B archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=cs753.T1.$NAME -DartifactId=$NAME
