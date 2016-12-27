package com.c9mj.platform.live.mvp.presenter.impl;

import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.LiveListItemBean;
import com.c9mj.platform.live.mvp.presenter.ILiveListPresenter;
import com.c9mj.platform.live.mvp.view.ILiveListFragment;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public class LiveListPresenterImpl implements ILiveListPresenter {

    private final ILiveListFragment view;

    public LiveListPresenterImpl(ILiveListFragment view) {
        this.view = view;
    }


    @Override
    public void getLiveList(int offset, int limit, String live_type, String game_type) {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getLiveList(offset, limit, live_type, game_type)
                .compose(RetrofitHelper.<List<LiveListItemBean>>handleLiveResult())
                .subscribe(new HttpSubscriber<List<LiveListItemBean>>() {
                    @Override
                    public void _onNext(List<LiveListItemBean> roomBeanList) {
                        view.updateRecyclerView(roomBeanList);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

}
