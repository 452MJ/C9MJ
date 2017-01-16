package com.c9mj.platform.gallery.mvp.presenter.impl;


import com.c9mj.platform.explore.api.ExploreAPI;
import com.c9mj.platform.gallery.mvp.model.bean.PhotoSetBean;
import com.c9mj.platform.gallery.mvp.presenter.IGalleryPresenter;
import com.c9mj.platform.gallery.mvp.view.IGalleryView;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import org.reactivestreams.Publisher;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/16.
 */

public class GalleryPresenterImpl implements IGalleryPresenter {

    private final IGalleryView view;

    public GalleryPresenterImpl(IGalleryView view) {
        this.view = view;
    }


    @Override
    public void getExploreSet(String photoSetId) {
        /**
         * photoSetId=00AJ0003|618725
         * typeId=0003
         * setId=618725
         */
        String[] photoSetArray = photoSetId.split("\\|");
        String typeId = photoSetArray[0].substring(photoSetArray[0].length() - 4, photoSetArray[0].length());//取hou四位
        String setId = photoSetArray[1];
        RetrofitHelper.getExploreHelper().create(ExploreAPI.class)
                .getExploreSet(typeId, setId)
                .flatMap(new Function<PhotoSetBean, Publisher<ArrayList<String>>>() {
                    @Override
                    public Publisher<ArrayList<String>> apply(PhotoSetBean photoSetBean) throws Exception {
                        ArrayList<String> imgList = new ArrayList<>();
                        for (PhotoSetBean.PhotosBean photosBean : photoSetBean.getPhotos()) {
                            imgList.add(photosBean.getImgurl());
                        }
                        return Flowable.just(imgList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<ArrayList<String>>() {
                    @Override
                    public void _onNext(ArrayList<String> imgList) {
                        view.updateViewPager(imgList);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }
}
