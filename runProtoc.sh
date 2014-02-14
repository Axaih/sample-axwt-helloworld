#!/bin/bash
B=$(find src/main/protobuf/ -name "*.proto")
echo $B
rm -R js_lib/pbdesc/com/mycompany/helloworld/*
rm -R src/main/java-gen/*
protoc  --proto_path=/usr/local/include --proto_path=src/main/protobuf  --java_out=src/main/java-gen/ --js_out=js_lib/pbdesc --plugin=/usr/local/bin/protoc-gen-js $B


