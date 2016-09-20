package com.c9mj.platform.live.api;


import com.c9mj.platform.live.mvp.model.bean.LiveBaseBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailDouyuBean;
import com.c9mj.platform.live.mvp.model.bean.LiveListItemBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface LiveAPI {

    public static final int LIMIT = 20;
    public static final String MAX_ID = "0";
    public static final String IMEI = "123456789101234";
    public static final String OS_TYPE = "Android";
    public static final String OS_VERSION = "5.1.1";
    public static final String VERSION = "3.6.6";
    public static final String LANG = "zh-cn";

    //请求获取不同游戏的直播列表
    @GET("/api/live/list/")
    Observable<LiveBaseBean<List<LiveListItemBean>>> getLiveList(
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("game_type") String game_type
    );

    //请求获取直播详情
    @GET("/api/live/detail/")
    Observable<LiveBaseBean<LiveDetailBean>> getLiveDetail(
            @Query("live_type") String live_type,
            @Query("live_id") String live_id,
            @Query("game_type") String game_type
    );

    //请求获取弹幕聊天室详情
    @GET
    Observable<LiveDetailDouyuBean> getDouyuDetail(
            @Url String url
    );
}
