package com.c9mj.platform.gallery.mvp.view;


import com.c9mj.platform.util.retrofit.exception.IErrorView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/30.
 */

public interface IGalleryView extends IErrorView {
    void updateViewPager(ArrayList<String> imgList);
}
