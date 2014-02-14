#!/bin/bash
# param $1 : namespace ex. helloworld.home.Home
# param $2 : output file ex. soy.helloworld.Home.boot.js 
closure-library/closure/bin/build/closurebuilder.py\
 --root=closure-library/ \
--root=soy/ --root=ax/ --root=helloworld/  \
 --compiler_jar="$CLOSURE_TOOLS/compiler.jar" \
 --compiler_flags="--create_source_map=source.map" \
 --compiler_flags="--compilation_level=ADVANCED_OPTIMIZATIONS" \
 --compiler_flags="--js=\"${HELLO_HOME}/js_lib/helloworld/css/$2.js\"" \
 --compiler_flags="--use_types_for_optimization" \
 --namespace="$1" \
 --output_mode=compiled \
 --output_file="$2.js" 

