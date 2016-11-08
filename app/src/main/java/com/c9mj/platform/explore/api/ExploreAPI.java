package com.c9mj.platform.explore.api;


import com.c9mj.platform.explore.mvp.model.bean.ExploreListBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface ExploreAPI {

    public static final int LIMIT = 20;

    @GET("/nc/article/headline/{explore_id}/{offset}-{limit}.html")
    Observable<ExploreListBean> getExploreList(
            @Path("explore_id") String explore_id,
            @Path("offset") int page,
            @Path("limit") int limit

    );

    @GET("/nc/article/{docid}/full.html")
    Observable<String> getExploreDetail(@Path("docid") String docid);

}
