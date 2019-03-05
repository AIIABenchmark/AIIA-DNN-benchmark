package com.xintongyuan.aibench;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class TestData {
    final static String TAG = "AIBench";

    final private Integer LABEL_OFFSET = 1;
    private String file_name = "";

    private Map<String, String> map_golden_data = null;
    private Map<String, String> map_golden_data_1001 = null;

    TestData(Activity activity, List<String> labelList, String val_file){
        if(labelList == null){
            Log.e(TAG, "labelList is null");
            return;
        }
        file_name = val_file.split("file:///android_asset/")[1];
        if(map_golden_data == null || map_golden_data_1001 == null){
            map_golden_data = new HashMap<String, String>();
            map_golden_data_1001 = new HashMap<String, String>();
            try{
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(activity.getAssets().open(file_name)));
                String line;
                while((line = reader.readLine()) != null){
                    String tmp[] = line.split(" ");
                    if(tmp.length != 0){
//                        Log.i(TAG, "img = "+tmp[0]+", label = "+labelList.get(Integer.valueOf(tmp[1])));
                        map_golden_data.put(tmp[0], labelList.get(Integer.valueOf(tmp[1])));
                        map_golden_data_1001.put(tmp[0], labelList.get(Integer.valueOf(tmp[1])+LABEL_OFFSET));
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getImageLabel(String imgName){

        return map_golden_data.get(imgName);
    }

    public String getImageLabel_1001(String imgName){
        return map_golden_data_1001.get(imgName);
    }
}
