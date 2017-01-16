package com.c9mj.platform.explore.mvp.presenter.impl;

import com.c9mj.platform.explore.api.ExploreAPI;
import com.c9mj.platform.explore.mvp.model.bean.ExploreListItemBean;
import com.c9mj.platform.explore.mvp.presenter.IExploreListPresenter;
import com.c9mj.platform.explore.mvp.view.IExploreListFragment;
import com.c9mj.platform.util.GsonHelper;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public class ExploreListPresenterImpl implements IExploreListPresenter {

    private final IExploreListFragment view;

    public ExploreListPresenterImpl(IExploreListFragment view) {
        this.view = view;
    }

    @Override
    public void getExploreList(String explore_id, int offset, int limit) {
        RetrofitHelper.getExploreHelper().create(ExploreAPI.class)
                .getExploreList(explore_id, offset, limit)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<JsonObject, Publisher<ArrayList<ExploreListItemBean>>>() {
                    @Override
                    public Publisher<ArrayList<ExploreListItemBean>> apply(JsonObject jsonObject) throws Exception {
                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            if (entry.getValue().isJsonArray()) {
                                JsonArray array = entry.getValue().getAsJsonArray();

                                ArrayList<ExploreListItemBean> list = new ArrayList<>();
                                for (JsonElement element : array) {
                                    list.add((ExploreListItemBean) GsonHelper.parseJson(element, ExploreListItemBean.class));
                                }
                                return Flowable.just(list);
                            }
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<ArrayList<ExploreListItemBean>>() {

                    @Override
                    public void _onNext(ArrayList<ExploreListItemBean> exploreListItemBeen) {
                        view.updateRecyclerView(exploreListItemBeen);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

}
