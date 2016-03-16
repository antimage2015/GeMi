package com.crazy.gemi.ui.utils;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance(){
        // 这里不用判断instance是否为空
        return instance;
    }
}
