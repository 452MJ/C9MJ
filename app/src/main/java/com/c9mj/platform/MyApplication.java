package com.c9mj.platform;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * author: LMJ
 * date: 2016/9/6
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
