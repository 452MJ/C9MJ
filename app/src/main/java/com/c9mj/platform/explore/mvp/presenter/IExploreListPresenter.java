package com.c9mj.platform.explore.mvp.presenter;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface IExploreListPresenter {
    void getLiveList(int offset, int limit, String game_type);//请求不同游戏的直播列表
}
