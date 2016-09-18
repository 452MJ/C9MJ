package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.bean.LiveRoomItemBean;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface ILiveRoomListFragment {
    void updateRecyclerView(List<LiveRoomItemBean> roomBeanList);//更新列表
    void showError(String message);//异常错误
}
