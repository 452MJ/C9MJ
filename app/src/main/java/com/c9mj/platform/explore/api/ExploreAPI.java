package com.c9mj.platform.explore.api;


import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface ExploreAPI {

    public static final int LIMIT = 20;

    @GET("/nc/article/list/{explore_id}/{offset}-{limit}.html")
    Flowable<JsonObject> getExploreList(
            @Path("explore_id") String explore_id,
            @Path("offset") int page,
            @Path("limit") int limit

    );

    @GET("/nc/article/{docid}/full.html")
    Flowable<String> getExploreDetail(@Path("docid") String docid);

}
