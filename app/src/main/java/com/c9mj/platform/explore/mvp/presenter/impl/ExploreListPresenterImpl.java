package com.c9mj.platform.explore.mvp.presenter.impl;

import android.content.Context;

import com.c9mj.platform.explore.mvp.presenter.IExploreListPresenter;
import com.c9mj.platform.explore.mvp.view.IExploreListFragment;
import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.bean.LiveListItemBean;
import com.c9mj.platform.live.mvp.presenter.ILiveListPresenter;
import com.c9mj.platform.live.mvp.view.ILiveListFragment;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public class ExploreListPresenterImpl implements IExploreListPresenter {

    private Context context;
    private IExploreListFragment view;

    public ExploreListPresenterImpl(Context context, IExploreListFragment view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public void getLiveList(int offset, int limit, String game_type) {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getLiveList(offset, limit, game_type, "panda")
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
