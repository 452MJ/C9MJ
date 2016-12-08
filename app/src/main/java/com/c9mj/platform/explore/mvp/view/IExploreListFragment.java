package com.c9mj.platform.explore.mvp.view;

import com.c9mj.platform.explore.mvp.model.bean.ExploreListItemBean;
import com.c9mj.platform.util.retrofit.exception.IErrorView;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface IExploreListFragment extends IErrorView {
    void updateRecyclerView(List<ExploreListItemBean> roomBeanList);//更新列表
}
