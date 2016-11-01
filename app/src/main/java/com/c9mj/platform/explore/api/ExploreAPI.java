package com.c9mj.platform.explore.api;


import com.c9mj.platform.explore.mvp.model.bean.NewsListBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface ExploreAPI {

    public static final int LIMIT = 20;

    @GET("http://c.m.163.com/nc/article/headline/T1348647909107/{page}-"+LIMIT+".html")
    Observable<NewsListBean> getNews(@Path("page") int page);

    @GET("http://c.m.163.com/nc/article/{docid}/full.html")
    Observable<String> getNewsDetail(@Path("docid") String docid);

}
