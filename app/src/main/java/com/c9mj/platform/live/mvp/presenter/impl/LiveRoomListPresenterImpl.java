package com.c9mj.platform.live.mvp.presenter.impl;

import android.content.Context;

import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.bean.LiveRoomBean;
import com.c9mj.platform.live.mvp.presenter.ILiveRoomListPresenter;
import com.c9mj.platform.live.mvp.view.ILiveRoomListFragment;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

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
    public void getAllRoomList(int offset, int limit, String client_sys) {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getAllLiveList(offset, limit, client_sys)
                .compose(RetrofitHelper.<List<LiveRoomBean>>handleLiveResult())
                .subscribe(new HttpSubscriber<List<LiveRoomBean>>() {
                    @Override
                    public void _onNext(List<LiveRoomBean> roomBeanList) {
                        view.updateRecyclerView(roomBeanList);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

    @Override
    public void getColumnRoomList(String cate_id, int offset, int limit, String client_sys) {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getColumnLiveList(cate_id, offset, limit, client_sys)
                .compose(RetrofitHelper.<List<LiveRoomBean>>handleLiveResult())
                .subscribe(new HttpSubscriber<List<LiveRoomBean>>() {
                    @Override
                    public void _onNext(List<LiveRoomBean> roomBeanList) {
                        view.updateRecyclerView(roomBeanList);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

}
