
/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author: y.haiyang@qq.com
 */

package com.example.pldroidlibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.Log;
import android.view.WindowManager;

public class ScreenOrientationUtils {
    private static final String TAG = "ScreenOrientationUtils";

    /**
     * 如果 当前activity 是非sensor默认则进行横屏
     */
    public static void setLandscape(Context context) {
        setLandscape(context, false);
    }

    /**
     * 如果 当前activity 是非sensor默认则进行横屏
     */
    public static void setLandscape(Context context, boolean force) {
        if (context == null || !(context instanceof Activity)) {
            Log.i(TAG, "setLandscape: context is null or context is not activity");
            return;
        }
        Activity activity = (Activity) context;
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR && !force) {
            return;
        }

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
    }

    /**
     * 如果 当前activity 是非sensor默认则进行竖屏
     */
    public static void setPortrait(Context context) {
        setPortrait(context, false);
    }

    /**
     * 如果 当前activity 是非sensor默认则进行竖屏
     */
    public static void setPortrait(Context context, boolean force) {
        if (context == null || !(context instanceof Activity)) {
            Log.i(TAG, "setPortrait: context is null or context is not activity");
            return;
        }
        Activity activity = (Activity) context;
        if (activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR && !force) {
            return;
        }
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }

    /**
     * 如果 当前activity 是非sensor默认则进行竖屏
     */
    public static void setSensor(Context context) {
        if (context == null || !(context instanceof Activity)) {
            Log.i(TAG, "setPortrait: context is null or context is not activity");
            return;
        }
        Activity activity = (Activity) context;

        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    /**
     * 是否是横屏
     */
    public static boolean isLandscape(Context context) {
        if (context == null) {
            Log.i(TAG, "isPortrait: context is null ");
            return false;
        }
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 是否是竖屏
     */
    public static boolean isPortrait(Context context) {
        if (context == null) {
            Log.i(TAG, "isPortrait: context is null ");
            return false;
        }
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 设置状态栏是否可见
     */
    public static void setStatusBarVisible(Context context, boolean isVisible) {
        if (context == null || !(context instanceof Activity)) {
            Log.i(TAG, "setStatusBarVisible: context is null or context is not activity");
            return;
        }
        Activity activity = (Activity) context;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        if (isVisible) {
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            activity.getWindow().setAttributes(lp);
        } else {
            lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            activity.getWindow().setAttributes(lp);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
