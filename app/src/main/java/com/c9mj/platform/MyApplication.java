package com.c9mj.platform;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.utils.SPUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.blankj.utilcode.utils.Utils;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.squareup.leakcanary.LeakCanary;

/**
 * author: liminjie
 * date: 2017/2/27
 * desc: MyApplication自定义
 */
public class MyApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;
    public static SPUtils spUtils;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LeakCanary.install(this);
        Utils.init(context);
        ToastUtils.init(true);
        spUtils = new SPUtils(getString(R.string.app_name));
        RxPaparazzo.register(this);
    }
}
