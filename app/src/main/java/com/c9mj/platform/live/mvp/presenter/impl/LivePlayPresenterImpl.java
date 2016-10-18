package com.c9mj.platform.live.mvp.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailPandaBean;
import com.c9mj.platform.live.mvp.presenter.ILivePlayPresenter;
import com.c9mj.platform.live.mvp.view.ILivePlayChatFragment;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: LMJ
 * date: 2016/9/20
 */

public class LivePlayPresenterImpl implements ILivePlayPresenter {

    private Context context;
    private ILivePlayChatFragment view;

    public LivePlayPresenterImpl(Context context, ILivePlayChatFragment view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getLiveDetail(String live_type, String live_id, String game_type) {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getLiveDetail(live_type, live_id, game_type)
                .compose(RetrofitHelper.<LiveDetailBean>handleLiveResult())
                .subscribe(new HttpSubscriber<LiveDetailBean>() {
                    @Override
                    public void _onNext(LiveDetailBean liveDetailBean) {
                        view.updateLiveDetail(liveDetailBean);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

    @Override
    public void getDanmuDetail(String live_id, String live_type) {
        if (live_type.equals("panda")) {
            RetrofitHelper.getPandaHelper().create(LiveAPI.class)
                    .getPandaChatroom(live_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LiveDetailPandaBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showError("弹幕服务器接口已过期，请刷新直播列表！");
                        }

                        @Override
                        public void onNext(LiveDetailPandaBean detailPandaBean) {
                            if (detailPandaBean.getErrno() == 0) {
                                view.updateDouyuDetail(detailPandaBean);
                            } else {
                                view.showError(detailPandaBean.getErrmsg());
                            }
                        }
                    });
        }else {
            view.showError("直播平台：" + live_type +"！不是熊猫TV的弹幕池！");
        }
    }
}
