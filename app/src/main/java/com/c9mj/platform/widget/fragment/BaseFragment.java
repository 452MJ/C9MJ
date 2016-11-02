package com.c9mj.platform.widget.fragment;

import android.app.Activity;

import com.c9mj.platform.util.SnackbarUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author: LMJ
 * date: 2016/9/5
 * Fragment基类
 */
public class BaseFragment extends SupportFragment{
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SnackbarUtil.init(activity.getWindow().getDecorView());
    }
}
