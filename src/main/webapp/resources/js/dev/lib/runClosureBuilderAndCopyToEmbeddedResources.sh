#!/bin/bash
# param $1 : namespace ex. helloworld.home.Home
# param $2 : output file ex. soy.helloworld.Home.boot 
./runClosureBuilder.sh $1 $2

./copyJsToEmbeddeResources.sh $2.js

