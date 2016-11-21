package com.c9mj.platform.live.mvp.presenter;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface ILiveListPresenter {
    void getLiveList(int offset, int limit, String live_type, String game_type);//请求不同游戏的直播列表
}
