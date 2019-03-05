# AIIABenchmark Overview

In recent years, the on-device deep learning applications are getting more and
more popular on mobile phones or IoT devices. It's a challenging task for the developers to deploy their
deep learning models in their mobile applications or IoT devices.

They need to optionally choose a cost-effective hardware solution (i.e. chips and boards),
then a proper inference framework, optionally utilizing quantization or compression
techniques regarding the precision-performance trade-off, and finally
run the model on one or more of heterogeneous computing devices. How to make an
appropriate decision among these choices is a tedious and time-consuming task.

The AI benchmark (i.e. **AIIABenchmark**)is currently based on the Android App as the benchmark framework,
which will be open to cover different chips and reasoning frameworks in the future. 
The results include speed and model accuracy, which will provide insights for developers.

# Introduction

This is a example of image classification application powered by AIIA. Please feel free to try them on your device.

## How To Use

#### 1.  [Android Image Classification](https://github.com/AIIABenchmark/AIIABenchmark)

​      This App based on the TensorFlow Lite engine can classify Images from your Devices.

​      Please download resources from [**App Resources Hub**](https://pan.baidu.com/s/1G91PqmAabQIjLyV3saeD5A) (psw: k04t)

​      Building in Android Studio with TensorFlow Lite AAR from JCenter

​      Import resource files into the device

​      Also refer to the [**TFLITE Models**](https://tensorflow.google.cn/lite/models)
```
adb shell mkdir /sdcard/Android/data/com.xintongyuan.aibench/files
adb shell mkdir /sdcard/Android/data/com.xintongyuan.aibench/files/images
adb shell mkdir /sdcard/Android/data/com.xintongyuan.aibench/files/models
adb shell mkdir /sdcard/Android/data/com.xintongyuan.aibench/files/models/tflite

adb push ./images/. /sdcard/Android/data/com.xintongyuan.aibench/files/images/
adb push ./tflite/. /sdcard/Android/data/com.xintongyuan.aibench/files/models/tflite/
```


#### 2.  Adding a model to run on existing architecture

​      Create a model class and inherit ImageClassifierTF

​      Dynamic binding in the main program

​      Please refer to the [**TensorFlow Lite example**](https://github.com/tensorflow/tensorflow/tree/master/tensorflow/lite/java).



#### 3.  Adding a new AI frameworks

​      AIBench supports several deep learning frameworks ( SNPE, HIAI,TENGINE and TensorFlow Lite) currently, which may require the following dependencies:


​      you need to download the [**SNPE**](https://developer.qualcomm.com/software/qualcomm-neural-processing-sdk), [**HIAI**](https://developer.huawei.com/consumer/cn/devservice/doc/3140202), [**TENGINE**](https://github.com/OAID/Tengine), [**TensorFlow Lite**](https://github.com/tensorflow/tensorflow/tree/master/tensorflow/lite), refer to the Demo and API.


​      Other content will be continuously updated.



## License
[Apache License 2.0](LICENSE).


