package com.c9mj.platform.live.mvp.presenter;

/**
 * author: LMJ
 * date: 2016/9/20
 */

public interface ILivePlayPresenter {
    void getLiveDetail(String live_type, String live_id, String game_type);//请求直播详情
    void getDanmuDetail(String url);//请求弹幕聊天细节参数
}
