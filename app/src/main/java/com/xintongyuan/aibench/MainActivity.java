package com.xintongyuan.aibench;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static String TAG = "AIBench";

    private Button bt_start;
    private TextView tv_model;
    private TextView tv_result;
    private Spinner sp_models;
    private Spinner sp_devices;
    private Handler handler;
    private ImageView show_img;


    private ImageClassifierTF classifiertf;
    private static final String imagePath = "images";
    private static String imageRootPath = "";
    private String[] image_list;  //存放图片文件名
    String[] models = new String[]{ "Mobilenet_v2","Inception_V3"};
    String[] tflite_runtimes = new String[]{"TFLITE", "NNAPI"};
    String test_runtime = "FTLITE";
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> devices_adapter;
    String test_model = "Mobilenet_v2";
    private String fps = "0";
    private java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.00");
    private List<String> tops_value = new ArrayList<String>();
    private List<Long> infer_time_list = new ArrayList<Long>();
    private long inference_time = 0;
    private BitmapFactory.Options opt;
    private Bitmap bitmap;

    private boolean flag_running = false;
    private int imgReadNum = 0;

    private List<String> image_name = new ArrayList<String>();
    private List<List<String>> image_tops = new ArrayList<List<String>>();
    private String top1_value = "";
    private String top5_value = "";
    int top1 = 0;
    int top5 = 0;
    long total_time = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_model = (TextView)findViewById(R.id.tv_model);
        sp_models = (Spinner)findViewById(R.id.sp_models);
        sp_devices = (Spinner)findViewById(R.id.sp_devices);
        tv_result = (TextView)findViewById(R.id.tv_result);
        bt_start = (Button)findViewById(R.id.bt_start);
        show_img = (ImageView) findViewById(R.id.show_image);


        handler = new Handler();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, models);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_models.setAdapter(adapter);
        sp_models.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "SELECT");
                test_model = models[position];
                Log.i(TAG, "selected model = " + test_model);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        devices_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, tflite_runtimes);
        devices_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_devices.setAdapter(devices_adapter);
        sp_devices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "RUNTIME");
                test_runtime = tflite_runtimes[position];
                Log.i(TAG, "selected runtime = " + test_runtime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
            final File modelsRoot = getBaseContext().getExternalFilesDir("models");
            if (modelsRoot == null) {
                Log.i(TAG, "modelsRoot is null");
            }
            if (!modelsRoot.isDirectory() && !modelsRoot.mkdir()) {
                Log.i(TAG, "Unable to create model root directory: " +
                        modelsRoot.getAbsolutePath());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        image_list = getSdcardImages(imagePath);

        test_model = tv_model.getText().toString();

        bt_start.setOnClickListener(this);

    }

    private String[] getSdcardImages(String imagePath)
    {
        ArrayList<String> imageList2 = new ArrayList<String>();
        String imageList[] = {};
        try {
            Log.i(TAG, "sdcardImgpath = " + getApplicationContext().getExternalFilesDir(imagePath));
            File sdImgPath = getApplicationContext().getExternalFilesDir(imagePath);
            if(sdImgPath.isDirectory()){
                imageRootPath = sdImgPath.getPath()+File.separator;
                Log.i(TAG, "imageRootPath = " + imageRootPath);
                for(File img: sdImgPath.listFiles()){
                    imageList2.add(img.getAbsolutePath());
                }
                imageList = imageList2.toArray(new String[0]);
                Arrays.sort(imageList);
                Log.i(TAG, "image size = " + imageList.length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return imageList;
    }

    void startTFLITESync()
    {
        Log.i(TAG, "startTestSync");
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bt_start.setText(R.string.bt_text_testing);
                    }
                });
                Log.i(TAG, "load model: " + test_model);
                try {
                    classifiertf = new ImageClassifierFloatMobilenet(MainActivity.this);
                    classifiertf.setUseNNAPI(false);
                    classifiertf.setNumThreads(5);
                    classifiertf.initlabe(MainActivity.this);
                    imgReadNum = 0;
                    total_time = 0;
                    top1 = 0;
                    top5 = 0;
                    infer_time_list.clear();
                    opt = new BitmapFactory.Options();
                    opt.inJustDecodeBounds = false;

                    for (imgReadNum = 0; (imgReadNum < image_list.length) && (flag_running = true); imgReadNum++) {
                        Map<String, Object> itemResult = new HashMap<String, Object>();
                        itemResult.put("image", image_list[imgReadNum]);
                        try {
                            bitmap = BitmapFactory.decodeFile(image_list[imgReadNum]);
                            Log.e(TAG, "bitmap." + bitmap.getWidth() + "****" + bitmap.getHeight());
                            classifiertf.convertBitmapToByteBuffer(bitmap);

                            long start_time = System.currentTimeMillis();
                            Log.e(TAG, "start time." + start_time);
                            classifiertf.runInference();
                            inference_time = System.currentTimeMillis() - start_time;
                            Log.e(TAG, "end time." + inference_time);
                            infer_time_list.add(inference_time);

                            image_name.add(image_list[imgReadNum].split(imageRootPath)[1]);
                            String str_result = classifiertf.runModel();
                            tops_value = Arrays.asList(str_result.split("\n"));
                            image_tops.add(tops_value);
                            itemResult.put("tops", str_result);

                        } catch (Exception e) {
                            Log.e(TAG, "TFLITE ERROR:.", e);
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Bitmap bm = BitmapFactory.decodeFile(image_list[imgReadNum], opt);
                                show_img.setImageBitmap(bitmap);

                                tv_result.setText("预测进度：" + imgReadNum + "/" + image_list.length);

                            }
                        });
                    }
                    classifiertf.close();
                    flag_running = false;
                    for (int j = 0; j < image_name.size(); j++) {
                        Log.i(TAG, "image_name" + image_name.get(j));
                        if (true == classifiertf.isTop1(image_name.get(j), image_tops.get(j))) {
                            top1++;
                        }
                        if (true == classifiertf.isTop5(image_name.get(j), image_tops.get(j))) {
                            top5++;
                        }
                    }
                    for (int i = 0; i < infer_time_list.size(); i++) {
                        total_time += infer_time_list.get(i);
                    }
                    fps = myformat.format(1000 / ((double) total_time / (double) image_list.length));
                    top1_value = myformat.format((double) top1 / (double) image_list.length * 100);
                    top5_value = myformat.format((double) top5 / (double) image_list.length * 100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            show_img.setImageBitmap(bitmap);
                            Log.i(TAG, "top1 = " + top1 + ", top5 = " + top5);
                            tv_result.setText("fps: " + fps + " top1: " + top1_value + "%, top5: " + top5_value + "%");
                            bt_start.setText(R.string.bt_text_start);
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, "Failed to initialize an image classifiertf.", e);
                }
                infer_time_list.clear();
                image_name.clear();
                image_tops.clear();
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_start:
                Log.i(TAG, "start test model: " + test_model);
                if(flag_running == false){
                    bt_start.setText(R.string.bt_text_prepare);
                    flag_running = true;
                    startTFLITESync();
                }else{
                    bt_start.setText(R.string.bt_text_start);
                    flag_running = false;
                }
                break;
            default:
                break;
        }
    }
}
