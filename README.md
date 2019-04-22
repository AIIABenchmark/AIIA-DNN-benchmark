<div align="center">
<img src="logo.png"/>
</div>

# AIIA DNN Benchmark Overview

The goal of the alliance is provide selection reference for application companies, 
and provide third-party evaluation results for chip companies.

The goal of **AIIA DNN benchmarks** is to objectively reflect the current state of AI accelerator capabilities, 
and all metrics are designed to provide an objective comparison dimension. 

We follow the principle of continuous iteration of the version, continuous enrichment of the scene, 
and continues to improve the AI chip type, and finally form a evaluation environment for the training 
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

## Five typical application scenario

**Test1: Object_Classification**


* Neural Network: Mobilenetv2 / Resnet101 / VGG16 / Inceptionv3  
* Image Resolution: 224 x 224 px |299 x 299 px  
* Metrics: fps / top1 / top5  
* Dataset: ImageNet (1k frames)  


**Test2: Object Detection**

* Neural Network: ssd_mobilenetv1 / ssd_mobilenetv2 / ssd_vgg16   
* Image Resolution: 300 x 300 px  
* Metrics: fps / mAP / mIoU  
* Dataset: PASCAL VOC2012 (1k frames)  

**Test3: Image_Super_Resolution**

* Neural Network: vdsr   
* Image Resolution: 256 x 256 px  
* Metrics: fps / PSNR(dB)    
* Dataset: PASCAL VOC2012 (1k frames)  

**Test4: Image_Segmentation**

* Neural Network: fcn  
* Image Resolution: 224 x 224 px  
* Metrics: fps / mIoU  
* Dataset: PASCAL VOC2012 (1k frames)  

**Test5: Face_Recognition**

* Neural Network: vgg16  
* Image Resolution: 224 x 224 px   
* Metrics: fps / Accuracy   
* Dataset: LFW (1k frames)  


## Benchmark Results

#### INT8 Inference

|	Product	|	Platform	|	Device	|	Framework	|	System	|	Test1: Object_Classification　　	|	　　　　　　　　　　	|	　　　　　　　　　　	|	　　　　　　　　　　	|	Test2: Object_Detection　	|	　　　　　　　　　　	|	　　　　　　　　　　	|	Test3: Image_Super_Resolution	|	Test4: Image_Segmentation	|	Test5: Face_Recognition	|
|	----------	|	----------	|	----------	|	----------	|	----------	|	-----	|	------	|	-----	|	------	|	-----	|	-----	|	-----	|	-----	|	-----	|	-----	|
|		|		|		|		|		|	　　mobilenet_v2	|	　　resnet101	|	　　　vgg16	|	　　inception_v3	|	　ssd_mobilenetv1	|	　ssd_mobilenetv2	|	　　ssd_vgg16	|	　　　vdsr	|	　　　　fcn	|	　　　vgg16	|
|		|		|		|		|		|	　FPS　TOP1　TOP5	|	　FPS　TOP1　TOP5	|	　FPS　TOP1　TOP5	|	　FPS　TOP1　TOP5	|	FPS　mAP　mIoU	|	　FPS　mAP　mIoU	|	　FPS　mAP　mIoU	|	　FPS　PSNR(dB)	|	　FPS　mAP　mIoU	|	　FPS　Accuracy	|
|	Huawei_Mate_20	|	kirin_980	|	NPU	|	HIAI	|	Android	|	101.90　71.3%　88.3%	|	43.78　71.9%　88.4%	|	32.38　64.3%　85%	|	58.32　75.8%　91.5%	|	65.68　0.84　0.83	|	　52.39　0.55　0.80	|	　14.06　0.89　0.79	|	　12.42　24.92	|	　　-　　-　　-	|	　-　　　-	|
|	ROC_RK3399_PC	|	CortexA72_x_2 CortexA53_x_4	|	CPU	|	TENGINE	|	Android	|	17.41　73.30%　91.30%	|	1.94　75.1%　93.1%	|	1.115　68.2%　89.4%	|	2.2　　77.5%　93.5%	|	　-　　　-　　-	|	　-　　　-　　-	|	　-　　　-　　-	|	　-　　　　-	|	　　-　　-　　-	|	　-　　　-	|

#### FLOAT16 Inference

|	Product	|	Platform	|	Device	|	Framework	|	System	|	Test1: Object_Classification	|	　　　　　　　　　　	|	　　　　　　　　　　	|	　　　　　　　　　　	|	Test2: Object_Detection　	|	　　　　　　　　　　	|	　　　　　　　　　　	|	Test3: Image_Super_Resolution	|	Test4: Image_Segmentation	|	Test5: Face_Recognition	|
|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|	----------	|
|		|		|		|		|		|	　mobilenet_v2	|	　　resnet101	|	　　　vgg16	|	　　inception_v3	|	　ssd_mobilenetv1	|	　ssd_mobilenetv2	|	　　ssd_vgg16	|	　　　vdsr	|	　　　fcn	|	　　　vgg16	|
|		|		|		|		|		|	FPS　TOP1　TOP5	|	　FPS　TOP1　TOP5	|	　FPS　TOP1　TOP5	|	　FPS　TOP1　TOP5	|	FPS　mAP　mIoU	|	　FPS　mAP　mIoU	|	　FPS　mAP　mIoU	|	　FPS　PSNR(dB)	|	　FPS　mAP　mIoU	|	　FPS　Accuracy	|
|	Huawei_Mate_20	|	kirin_980	|	NPU	|	HIAI	|	Android	|	54.2　70.7%　88.2%	|	21.98　72.3%　89.2%	|	13.53　66.1%　85.2%	|	32.93　75.7%　92.3%	|	35　0.86　0.84	|	　29.97　0.62　0.78	|	　7.276　0.96　0.84	|	　7.64　24.92	|	　1.39　-　　-	|	　-　　　-	|



## License
[Apache License 2.0](LICENSE).


