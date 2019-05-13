#!/bin/bash

# old non-maven method
# javac -cp ".:./jnativehook-2.1.0.jar" *.java

cd keyboardhook
mvn package
cd target
mv *dependencies.jar ../..
cd ../..
chmod +x *jar
