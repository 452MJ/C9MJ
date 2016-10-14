package com.c9mj.platform;

import android.app.Application;

import com.c9mj.platform.util.ToastUtil;

/**
 * author: LMJ
 * date: 2016/9/6
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtil.init(getApplicationContext());
    }
}
