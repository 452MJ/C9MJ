package com.c9mj.platform.explore.mvp.presenter.impl;

import android.content.Context;

import com.c9mj.platform.explore.api.ExploreAPI;
import com.c9mj.platform.explore.mvp.model.bean.ExploreListBean;
import com.c9mj.platform.explore.mvp.model.bean.ExploreListItemBean;
import com.c9mj.platform.explore.mvp.presenter.IExploreListPresenter;
import com.c9mj.platform.explore.mvp.view.IExploreListFragment;
import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.bean.LiveListItemBean;
import com.c9mj.platform.live.mvp.presenter.ILiveListPresenter;
import com.c9mj.platform.live.mvp.view.ILiveListFragment;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.R.attr.offset;
import static android.R.id.message;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public class ExploreListPresenterImpl implements IExploreListPresenter {

    Context context;
    IExploreListFragment view;

    public ExploreListPresenterImpl(Context context, IExploreListFragment view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public void getExploreList(String explore_id, int offset, int limit) {
        RetrofitHelper.getExploreHelper().create(ExploreAPI.class)
                .getExploreList(explore_id, offset, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<ExploreListBean>() {
                    @Override
                    public void _onNext(ExploreListBean exploreListBean) {
                        Map<String, List<ExploreListItemBean>> data = exploreListBean.getNewsListItem();
                        for (Map.Entry<String, List<ExploreListItemBean>> entry :
                                data.entrySet()) {
                            view.updateRecyclerView(entry.getValue());
                        }
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

}
