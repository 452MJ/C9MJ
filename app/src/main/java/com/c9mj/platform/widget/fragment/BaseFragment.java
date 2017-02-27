package com.c9mj.platform.widget.fragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * author: liminjie
 * date: 2017/2/27
 * desc: BaseFragment基类
 */

public class BaseFragment extends SwipeBackFragment {

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
