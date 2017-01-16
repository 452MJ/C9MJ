package com.c9mj.platform.explore.mvp.presenter.impl;


import com.c9mj.platform.explore.api.ExploreAPI;
import com.c9mj.platform.explore.mvp.model.bean.ExploreDetailBean;
import com.c9mj.platform.explore.mvp.presenter.IExploreDetailPresenter;
import com.c9mj.platform.explore.mvp.view.IExploreDetailView;
import com.c9mj.platform.util.GsonHelper;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/30.
 */

public class ExploreDetailPresenterImpl implements IExploreDetailPresenter {

    private final IExploreDetailView view;

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
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(detailBean -> {
                    //成功得到新闻详情后，先保存bean，并刷新相关新闻列表
                    view.updateExploreDetail(detailBean);
                    view.updateRelativeSys(detailBean.getRelative_sys());
                })
                .flatMap(new Function<ExploreDetailBean, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(ExploreDetailBean detailBean) throws Exception {
                        //在这里处理html代码
                        String html = detailBean.getBody();

                        //添加Javascript脚本
                        html = "<script type=\"application/javascript\" language=\"javascript\"> \n" +
                                "    function startGallary(e){\n" +
                                "        window.jsObj.startGallaryOnAndroid(e);\n" +
                                "    }\n" +
                                "</script>\n" + "\n" + html;

                        List<ExploreDetailBean.ImgBean> imgList = detailBean.getImg();

                        for (int i = 0; i < imgList.size(); i++) {
                            ExploreDetailBean.ImgBean imgBean = imgList.get(i);
                            String ref = imgBean.getRef();//标志
                            String imgCode = "<img src=\"" + imgBean.getSrc() + "\" id=\"img" + i + "\" width=100% onclick=\"startGallary(" + i + ")\"/><p style=color:757575;font-size:12px align=center>" + imgBean.getAlt() + "</p>";
                            //查找到需要插入的位置，并添加图片url
                            StringBuilder buffer = new StringBuilder(html);
                            int pos = buffer.indexOf(ref);
                            buffer.insert(pos, imgCode);
                            html = buffer.toString();
                        }

                        return Flowable.just(html);
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
