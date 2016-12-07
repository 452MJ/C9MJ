package com.c9mj.platform.widget.fragment;

import android.app.Activity;
import android.view.animation.Animation;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * author: LMJ
 * date: 2016/9/5
 * Fragment基类
 */
public class BaseFragment extends SwipeBackFragment{

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
         return new DefaultHorizontalAnimator();
    }
}
