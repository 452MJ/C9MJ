package com.c9mj.platform.live.api;


import com.c9mj.platform.live.bean.LiveBaseBean;
import com.c9mj.platform.live.bean.LiveIndicatorBean;
import com.c9mj.platform.live.bean.LiveRoomItemBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface LiveAPI {

    public static final String CLIENT_SYS = "client_sys";
    public static final int LIMIT = 20;

    /**
     * 请求顶部栏目标题
     * @return
     */
    @GET("/api/v1/getColumnList?client_sys=android")
    Observable<LiveBaseBean<List<LiveIndicatorBean>>> getColumnList();

    /**
     * 请求全部直播
     * @param offset 分页偏移量
     * @return
     */
    @GET("/api/v1/live")
    Observable<LiveBaseBean<List<LiveRoomItemBean>>> getAllLiveList(
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("client_sys") String client_sys
    );

    /**
     * 根据cate_id请求不同分类节目的直播
     * @param cate_id
     * @param offset
     * @param limit
     * @param client_sys
     * @return
     */
    @GET("/api/v1/getColumnRoom/{cate_id}")
    Observable<LiveBaseBean<List<LiveRoomItemBean>>> getColumnLiveList(
            @Path("cate_id") String cate_id,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("client_sys") String client_sys
    );

    /**
     * 请求直播间信息
     * @param room_id
     * @param aid
     * @param client_sys
     * @param ne
     * @param support_pwd
     * @param time
     * @param auth
     * @return
     */
    @GET("/api/v1/room/{room_id}")
    Observable<LiveBaseBean<List<LiveRoomItemBean>>> getRoomInfo(
            @Path("room_id") String room_id,
            @Query("aid") String aid,
            @Query("client_sys") String client_sys,
            @Query("ne") String ne,
            @Query("support_pwd") String support_pwd,
            @Query("time") String time,
            @Query("auth") String auth
    );
}
