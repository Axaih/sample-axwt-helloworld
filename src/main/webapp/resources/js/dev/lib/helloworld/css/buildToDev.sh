#!/bin/bash
# param $1 : namespace ex. soy.shorts.Home.boot


java -jar $CLOSURE_TOOLS/css-import-res.jar $1.gss > $1.noimport.gss

java -jar $CLOSURE_TOOLS/closure-stylesheets.jar  --allow-unrecognized-functions  --allow-unrecognized-properties --rename NONE --output-renaming-map-format JSON --output-renaming-map $1.json -o $1.min1.css $1.noimport.gss 

./copymap.sh $1.json 
csso $1.min1.css > $1.css

./copycss.sh $1.css

java -jar $CLOSURE_TOOLS/closure-stylesheets.jar  --allow-unrecognized-functions  --allow-unrecognized-properties --rename NONE --output-renaming-map-format CLOSURE_UNCOMPILED --output-renaming-map $1.uncompiled.js -o $1.css $1.noimport.gss 

