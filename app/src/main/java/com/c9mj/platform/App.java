package com.c9mj.platform;

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
public class App extends Application {

    protected static App instance;
    private SPUtils spUtils;

    public static SPUtils getSpUtils() {
        return getInstance().spUtils;
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return getInstance().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LeakCanary.install(this);
        Utils.init(getApplicationContext());
        ToastUtils.init(true);
        spUtils = new SPUtils(getString(R.string.app_name));
        RxPaparazzo.register(this);
    }

}
