#!/bin/bash
# param $1 : namespace ex. soy.shorts.Home.boot


java -jar $CLOSURE_TOOLS/css-import-res.jar $1.gss > $1.noimport.gss

java -jar $CLOSURE_TOOLS/closure-stylesheets.jar  --allow-unrecognized-functions  --allow-unrecognized-properties --rename CLOSURE --output-renaming-map-format JSON --output-renaming-map $1.json -o $1.css $1.noimport.gss 

./copymap.sh $1.json 
./copycss.sh $1.css

java -jar $CLOSURE_TOOLS/closure-stylesheets.jar  --allow-unrecognized-functions  --allow-unrecognized-properties --rename CLOSURE --output-renaming-map-format CLOSURE_COMPILED --output-renaming-map $1.js -o $1.css $1.noimport.gss 

