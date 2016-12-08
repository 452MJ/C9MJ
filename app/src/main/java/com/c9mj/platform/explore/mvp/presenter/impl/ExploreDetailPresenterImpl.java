package com.c9mj.platform.explore.mvp.presenter.impl;


import com.c9mj.platform.explore.mvp.presenter.IExploreDetailPresenter;
import com.c9mj.platform.explore.mvp.view.IExploreDetailView;

/**
 * Created by Administrator on 2016/11/30.
 */

public class ExploreDetailPresenterImpl implements IExploreDetailPresenter {

    IExploreDetailView view;

    public ExploreDetailPresenterImpl(IExploreDetailView view) {
        this.view = view;
    }
}
