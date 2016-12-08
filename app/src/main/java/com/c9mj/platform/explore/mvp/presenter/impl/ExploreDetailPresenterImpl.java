package com.c9mj.platform.explore.mvp.presenter.impl;


import com.c9mj.platform.explore.api.ExploreAPI;
import com.c9mj.platform.explore.mvp.model.bean.ExploreDetailBean;
import com.c9mj.platform.explore.mvp.model.bean.ExploreListItemBean;
import com.c9mj.platform.explore.mvp.presenter.IExploreDetailPresenter;
import com.c9mj.platform.explore.mvp.view.IExploreDetailView;
import com.c9mj.platform.util.GsonHelper;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.R.attr.offset;
import static android.R.id.list;

/**
 * Created by Administrator on 2016/11/30.
 */

public class ExploreDetailPresenterImpl implements IExploreDetailPresenter {

    IExploreDetailView view;

    public ExploreDetailPresenterImpl(IExploreDetailView view) {
        this.view = view;
    }

    @Override
    public void getExploreDetail(String doc_id) {
        RetrofitHelper.getExploreHelper().create(ExploreAPI.class)
                .getExploreDetail(doc_id)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<JsonObject, Publisher<ExploreDetailBean>>() {
                    @Override
                    public Publisher<ExploreDetailBean> apply(JsonObject jsonObject) throws Exception {
                        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                            JsonElement element = entry.getValue();
                            ExploreDetailBean detailBean = (ExploreDetailBean) GsonHelper.parseJson(element, ExploreDetailBean.class);
                            return Flowable.just(detailBean);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<ExploreDetailBean>() {
                    @Override
                    public void accept(ExploreDetailBean detailBean) throws Exception {
                        //成功得到新闻详情后，先保存bean，并刷新相关新闻列表
                        view.updateExploreDetail(detailBean);
                        view.updateRecyclerView(detailBean.getRelative_sys());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ExploreDetailBean, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(ExploreDetailBean detailBean) throws Exception {
                        //在这里加工html代码
                        String html = detailBean.getBody();
                        List<ExploreDetailBean.ImgBean> imgList = detailBean.getImg();

                        for (ExploreDetailBean.ImgBean imgBean : imgList) {
                            String ref = imgBean.getRef();//注释标签
                            String imgCode = "<img src=\"" + imgBean.getSrc() + "\"  alt=\"" + imgBean.getAlt() + "\" />";
                            //查找到需要插入的位置，并添加图片url
                            int pos = html.indexOf(ref);

                        }

                        return Flowable.just("html");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new HttpSubscriber<String>() {
                    @Override
                    public void _onNext(String html) {
                        //最终刷新详情内容页面
                        view.updateWebView(html);
                    }

                    @Override
                    public void _onError(String message) {

                    }
                });
    }

}
