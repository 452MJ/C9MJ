package com.c9mj.platform.gallery.mvp.presenter.impl;


import com.c9mj.platform.gallery.mvp.presenter.IGalleryPresenter;
import com.c9mj.platform.gallery.mvp.view.IGalleryView;

/**
 * Created by Administrator on 2016/12/16.
 */

public class GalleryPresenterImpl implements IGalleryPresenter {

    IGalleryView view;

    public GalleryPresenterImpl(IGalleryView view) {
        this.view = view;
    }
}
