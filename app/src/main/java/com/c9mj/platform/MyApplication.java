package com.c9mj.platform;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.c9mj.platform.util.SpHelper;
import com.c9mj.platform.util.ToastUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * author: LMJ
 * date: 2016/9/6
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LeakCanary.install(this);
        ToastUtil.init(getApplicationContext());
        SpHelper.init(getApplicationContext());
    }
}
