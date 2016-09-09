package com.c9mj.platform.live.mvp.presenter.impl;

import android.content.Context;

import com.c9mj.platform.live.bean.LiveIndicatorBean;
import com.c9mj.platform.live.mvp.presenter.ILiveRoomListPresenter;
import com.c9mj.platform.live.mvp.view.ILiveRoomListFragment;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;
import com.c9mj.platform.live.api.LiveAPI;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public class LiveRoomListPresenterImpl implements ILiveRoomListPresenter {

    private Context context;
    private ILiveRoomListFragment view;

    public LiveRoomListPresenterImpl(Context context, ILiveRoomListFragment view) {
        this.context = context;
        this.view = view;
    }



    @Override
    public void getAllRoomList() {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getColumnList()
                .compose(RetrofitHelper.<List<LiveIndicatorBean>>handleLiveResult())
                .subscribe(new HttpSubscriber<List<LiveIndicatorBean>>() {
                    @Override
                    public void _onNext(List<LiveIndicatorBean> columnBeanList) {
                        view.updateRecyclerView(columnBeanList);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

    @Override
    public void getColumnRoomList() {

    }

}
