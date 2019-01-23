#!/bin/bash
ROOT="$(dirname $0)"
JAVA_PACKAGE=com.rushingvise.reactcpp
JAVA_OUTPUT_FOLDER=$ROOT/java/com/rushingvise/reactcpp
JNI_OUTPUT_FOLDER=$ROOT/jni
CPP_OUTPUT_FOLDER=$ROOT/cpp
OBJC_PREFIX="RV"
OBJC_OUTPUT_FOLDER=$ROOT/objc
IDL_FILE=$ROOT/react.djinni
YAML_OUT=$ROOT
YAML_OUT_FILE=react.yaml

set -e

$ROOT/../src/run \
   --java-out $JAVA_OUTPUT_FOLDER \
   --java-package $JAVA_PACKAGE \
   --ident-java-field mFooBar \
   --cpp-out $CPP_OUTPUT_FOLDER \
   --jni-out $JNI_OUTPUT_FOLDER \
   --ident-jni-class NativeFooBar \
   --ident-jni-file NativeFooBar \
   --objc-out $OBJC_OUTPUT_FOLDER \
   --objc-type-prefix $OBJC_PREFIX \
   --objcpp-out $OBJC_OUTPUT_FOLDER \
   --idl $IDL_FILE \
   --yaml-out $YAML_OUT \
   --yaml-out-file $YAML_OUT_FILE \
