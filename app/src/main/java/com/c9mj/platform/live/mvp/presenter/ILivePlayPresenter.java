package com.c9mj.platform.live.mvp.presenter;

import com.c9mj.platform.live.mvp.model.LivePandaBean;

/**
 * author: LMJ
 * date: 2016/9/20
 */

public interface ILivePlayPresenter {
    void getLiveDetail(String live_type, String live_id, String game_type);//请求直播详情

    void getDanmuDetail(String roomid, String live_type);//请求弹幕聊天细节参数

    void connectToChatRoom(String live_id, LivePandaBean pandaBean);//连接弹幕聊天室

    void closeConnection();//断开连接
}
