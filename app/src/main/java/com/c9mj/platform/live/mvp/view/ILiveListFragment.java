package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.mvp.model.bean.LiveListItemBean;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface ILiveListFragment extends IErrorView {
    void updateRecyclerView(List<LiveListItemBean> roomBeanList);//更新列表
}
