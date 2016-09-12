package com.c9mj.platform.util;

import android.content.Context;
import android.widget.Toast;

/**
 * author: LMJ
 * date: 2016/9/7
 * 用于维护唯一Toastd工具类
 */
public class ToastUtil {
    private static Toast toast;
    private static boolean isShow = false;

    private static void initToast(Context context) {
        if (toast == null){
            synchronized (ToastUtil.class){
                toast = new Toast(context.getApplicationContext());
            }
        }
    }

    public static void show(Context context, CharSequence content, int duration){
        initToast(context);
        if (isShow == true){
            isShow = false;
            toast.cancel();
        }
        toast = Toast.makeText(context, content, duration);
        toast.show();
        isShow = true;
    }

    public static void show(Context context, CharSequence content){
        show(context, content, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId){
        show(context, context.getString(resId), Toast.LENGTH_SHORT);
    }


}
