### Bindings in IDE

To generate bindings within IDE you can use the following run parameters:

#### Support Library

Run in `support-lib` directory with the following params:
 `--java-out ./java/com/rushingvise/reactcpp --java-package com.rushingvise.reactcpp --ident-java-field mFooBar --cpp-out ./cpp --jni-out ./jni --ident-jni-class NativeFooBar --ident-jni-file NativeFooBar --objc-out ./objc --objc-type-prefix RV --objcpp-out ./objc --idl ./react.djinni --yaml-out . --yaml-out-file react.yaml`

#### Sample project

Run in `example-react-native/ExampleProject/idl` with the following params:
`--java-out ./../android/app/src/main/java/com/rushingvise/reactcppexample/jni --java-package com.rushingvise.reactcppexample.jni --ident-java-field mFooBar --cpp-out ./../cpp/gen --jni-out ./../android/app/src/main/cpp/gen --ident-jni-class NativeFooBar --ident-jni-file NativeFooBar --objc-out ./../ios/ExampleProject/Generated --objc-type-prefix RC --objcpp-out ./../ios/ExampleProject/Generated --idl ./../idl/main.djinni`
