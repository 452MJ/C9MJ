package com.c9mj.platform.explore.api;


import com.c9mj.platform.gallery.mvp.model.bean.PhotoSetBean;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface ExploreAPI {

    int LIMIT = 20;

    @GET("/nc/article/list/{explore_id}/{offset}-{limit}.html")
    Flowable<JsonObject> getExploreList(
            @Path("explore_id") String explore_id,
            @Path("offset") int page,
            @Path("limit") int limit

    );

    @GET("/nc/article/{docid}/full.html")
    Flowable<JsonObject> getExploreDetail(@Path("docid") String docid);

    @GET("/photo/api/set/{typeid}/{setid}.json")
    Flowable<PhotoSetBean> getExploreSet(
            @Path("typeid") String typeid,
            @Path("setid") String setid
    );
}
