package com.c9mj.platform.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.c9mj.platform.R;

/**
 * author: LMJ
 * date: 2016/9/1
 * SharedPreferences工具类
 */
public class SpHelper {

    /*************
     * 默认保存为String类型（int与boolean在末尾添加说明）
     **************/
    public static final String BOOLEAN_XX = "boolean_XX";//boolean型value
    public static final String STRING_XX = "string_XX";//String型value
    public static final String INT_XX = "int_XX";        //int型value
    //explore
    public static final String STRING_TITLE = "explore_title";
    public static final String STRING_TITLE_SELECTED = "explore_title_selected";
    public static final String STRING_TITLE_UNSELECTED = "explore_title_unselected";
    //user
    public static final String STRING_USER = "user_background";
    private static Context context;
    private static SpHelper instance = null;

    /****************************************************************************/

    private SpHelper(Context ctx) {
        context = ctx;
    }

    public static void init(Context ctx) {
        if (instance == null) {
            synchronized (SpHelper.class) {
                if (instance == null) {
                    context = ctx;
                    instance = new SpHelper(ctx.getApplicationContext());
                }
            }
        }
    }

    private static SharedPreferences getSP() {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    /************
     * 字符串String(key-value)
     *************/
    public static void setString(String key, String value) {
        getSP().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getSP().getString(key, "");
    }

    /***************
     * 整型int(key-value)
     ****************/
    public static void setInt(String key, int value) {
        getSP().edit().putInt(key, value).apply();
    }

    public static int getInt(String key) {
        return getSP().getInt(key, 0);
    }

    /**********
     * 布尔值boolean(key-value)
     ****************/
    public static void setBoolean(String key, boolean value) {
        getSP().edit().putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(String key) {
        return getSP().getBoolean(key, false);
    }

    /**********
     * 浮点值float(key-value)
     ****************/
    public static void setFloat(String key, float value) {
        getSP().edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return getSP().getFloat(key, 0);
    }

    /**********
     * 长浮点值long(key-value)
     ****************/
    public static void setLong(String key, long value) {
        getSP().edit().putLong(key, value).apply();
    }

    public static float getLong(String key) {
        return getSP().getLong(key, 0);
    }


}
