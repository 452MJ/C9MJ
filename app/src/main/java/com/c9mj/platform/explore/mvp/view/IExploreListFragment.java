package com.c9mj.platform.explore.mvp.view;

import com.c9mj.platform.live.mvp.model.bean.LiveListItemBean;
import com.c9mj.platform.util.retrofit.exception.IErrorView;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface IExploreListFragment extends IErrorView {
    void updateRecyclerView(List<LiveListItemBean> roomBeanList);//更新列表
}
