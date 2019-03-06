<div align="center">
<img src="logo.png"/>
</div>

# AIIABenchmark Overview

The goal of the alliance is provide selection reference for application companies, 
and provide third-party evaluation results for chip companies.

The goal of **AIIA DNN benchmarks** is to objectively reflect the current state of AI accelerator capabilities, 
and all metrics are designed to provide an objective comparison dimension. 

We follow the principle of continuous iteration of the version, continuous enrichment of the scene, 
and continuous improvement of the AI chip type, and finally form a evaluation environment for the training 
and inference including the terminal and the cloud.

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


