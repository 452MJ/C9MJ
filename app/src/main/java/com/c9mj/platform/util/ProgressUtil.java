package com.c9mj.platform.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.c9mj.platform.R;

/**
 * author: liminjie
 * date: 2017/2/7
 * desc: ProgressUtil等待框工具类.
 */

public class ProgressUtil {

    private static AlertDialog dialog;

    public static void show(@NonNull Context context) {
        dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.layout_loading)
                .setCancelable(true);
        dialog = builder.create();
        dialog.show();
    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
