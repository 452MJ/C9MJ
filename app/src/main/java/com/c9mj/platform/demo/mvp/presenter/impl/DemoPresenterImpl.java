package com.c9mj.platform.demo.mvp.presenter.impl;

import com.c9mj.platform.demo.mvp.presenter.IDemoPresenter;
import com.c9mj.platform.demo.mvp.view.IDemoView;


public class DemoPresenterImpl implements IDemoPresenter {

    private final IDemoView view;

    public DemoPresenterImpl(IDemoView view) {
        this.view = view;
    }
}
