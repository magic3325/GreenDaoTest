package com.water.greendaotest;

import android.app.Application;

public class MyApplication extends Application {
    public static MyApplication instances;

    @Override
    public void onCreate() {
        super.onCreate();
        instances =this;
    }

    public static MyApplication getInstances(){
        return instances;
    }

}
