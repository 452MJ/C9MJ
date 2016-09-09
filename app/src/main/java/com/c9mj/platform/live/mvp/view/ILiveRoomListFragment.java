package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.bean.LiveIndicatorBean;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface ILiveRoomListFragment {
    void updateRecyclerView(List<LiveIndicatorBean> columnBeanList);//更新列表
    void showError(String message);//异常错误
}
