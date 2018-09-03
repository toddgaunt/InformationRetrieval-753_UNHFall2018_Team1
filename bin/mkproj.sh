#!/bin/sh

NAME=$1

if [[ "$1" == "" ]]
	echo "mkproj <project-name>"
	exit 1 
fi

mvn -B archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DgroupId=753T1$NAME -DartifactId=$NAME
