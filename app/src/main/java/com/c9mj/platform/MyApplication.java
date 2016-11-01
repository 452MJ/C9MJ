package com.c9mj.platform;

import android.app.Application;
import android.content.Context;

import com.c9mj.platform.util.ToastUtil;

/**
 * author: LMJ
 * date: 2016/9/6
 */
public class MyApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ToastUtil.init(getApplicationContext());
    }


    public static Context getContext() {
        return context;
    }
}
