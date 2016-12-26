package com.c9mj.platform.live.api;


import com.c9mj.platform.live.mvp.model.LiveBaseBean;
import com.c9mj.platform.live.mvp.model.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.LiveListItemBean;
import com.c9mj.platform.live.mvp.model.LivePandaBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface LiveAPI {

    int LIMIT = 20;

    //请求获取不同游戏的直播列表
    @GET("/api/live/list/")
    Flowable<LiveBaseBean<List<LiveListItemBean>>> getLiveList(
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("live_type") String live_type,
            @Query("game_type") String game_type
    );

    //请求获取直播详情
    @GET("/api/live/detail/")
    Flowable<LiveBaseBean<LiveDetailBean>> getLiveDetail(
            @Query("live_type") String live_type,
            @Query("live_id") String live_id,
            @Query("game_type") String game_type
    );

    //请求获取弹幕聊天室详情
    @GET("/ajax_chatinfo")
    Flowable<LivePandaBean> getPandaChatroom(
            @Query("roomid") String roomid
    );
}
