package com.c9mj.platform.widget.recyclerview;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

/**
 * author: liminjie
 * date: 2017/2/27
 * desc: CustomLoadMoreView管理列表底部View
 */

public final class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.layout_loadmore;
    }

    /**
     * 如果返回true，数据全部加载完毕后会隐藏加载更多
     * 如果返回false，数据全部加载完毕后会显示getLoadEndViewId()布局
     */
    @Override
    public boolean isLoadEndGone() {
        return false;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.progressbar;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.tv_fail;
    }

    /**
     * isLoadEndGone()为true，可以返回0
     * isLoadEndGone()为false，不能返回0
     */
    @Override
    protected int getLoadEndViewId() {
        return R.id.tv_end;
    }
}