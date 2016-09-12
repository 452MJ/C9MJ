package com.c9mj.platform.live.mvp.presenter;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface ILiveRoomListPresenter {
    void getAllRoomList(int offset, int limit, String client_sys);//请求全部直播
    void getColumnRoomList(String cate_id, int offset, int limit, String clientSys);//请求不同栏目直播
}
