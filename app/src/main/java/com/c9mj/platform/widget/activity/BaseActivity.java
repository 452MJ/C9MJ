package com.c9mj.platform.widget.activity;

import android.os.Bundle;

import com.c9mj.platform.util.SnackbarUtil;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by Administrator on 2016/11/2.
 */

public class BaseActivity extends SwipeBackActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SnackbarUtil.init(getWindow().getDecorView());
    }
}
