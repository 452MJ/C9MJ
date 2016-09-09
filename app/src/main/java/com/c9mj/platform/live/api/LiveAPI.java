package com.c9mj.platform.live.api;


import com.c9mj.platform.live.bean.LiveBaseBean;
import com.c9mj.platform.live.bean.LiveIndicatorBean;

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

    /**
     * 请求顶部栏目标题
     * @return
     */
    @GET("/api/v1/getLiveRoomList?client_sys=android")
    Observable<LiveBaseBean<List<LiveIndicatorBean>>> getColumnList();

    /**
     * 请求全部直播
     * @param offset 分页偏移量
     * @return
     */
    @GET("/api/v1/live")
    Observable<LiveBaseBean<List<LiveIndicatorBean>>> getAllLiveList(
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
    Observable<LiveBaseBean<List<LiveIndicatorBean>>> getColumnLiveList(
            @Path("cate_id") String cate_id,
            @Query("offset") int offset,
            @Query("limit") int limit,
            @Query("client_sys") String client_sys
    );
}
