package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.mvp.model.LiveListItemBean;
import com.c9mj.platform.util.retrofit.exception.IErrorView;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface ILiveListFragment extends IErrorView {
    void updateRecyclerView(List<LiveListItemBean> roomBeanList);//更新列表
}
