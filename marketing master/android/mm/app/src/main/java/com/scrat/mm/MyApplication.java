package com.scrat.mm;

import android.app.Application;

public class MyApplication extends Application {

    public static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }


    public static Application shareApplication() {
        return application;
    }
}
