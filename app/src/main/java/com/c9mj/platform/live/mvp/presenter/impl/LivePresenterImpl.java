package com.c9mj.platform.live.mvp.presenter.impl;

import android.content.Context;

import com.c9mj.platform.live.bean.LiveIndicatorBean;
import com.c9mj.platform.live.mvp.presenter.ILivePresenter;
import com.c9mj.platform.live.mvp.view.ILiveFragment;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;
import com.c9mj.platform.live.api.LiveAPI;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public class LivePresenterImpl implements ILivePresenter {

    private Context context;
    private ILiveFragment view;

    public LivePresenterImpl(Context context, ILiveFragment view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getColumnList() {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getColumnList()
                .compose(RetrofitHelper.<List<LiveIndicatorBean>>handleLiveResult())
                .subscribe(new HttpSubscriber<List<LiveIndicatorBean>>() {
                    @Override
                    public void _onNext(List<LiveIndicatorBean> columnBeanList) {
                        view.updateIndicator(columnBeanList);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }
}
