package com.c9mj.platform.util;

import android.content.Context;
import android.widget.Toast;

/**
 * author: LMJ
 * date: 2016/9/7
 */
public class ToastUtil {
    private static Toast toast;
    private static boolean isShow = false;

    public static void initToast(Context context) {
        if (toast == null){
            synchronized (ToastUtil.class){
                toast = new Toast(context.getApplicationContext());
            }
        }
    }

    public static void show(Context context, String content){
        initToast(context);
        if (isShow == true){
            isShow = false;
            toast.cancel();
        }
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.show();
        isShow = true;
    }
}
