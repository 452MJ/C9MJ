package com.c9mj.platform.explore.mvp.presenter;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface IExploreListPresenter {
    void getExploreList(String explore_id, int offset, int limit);//请求不同栏目的新闻列表
}
