package com.c9mj.platform.util.retrofit;


import com.c9mj.platform.util.retrofit.bean.LiveBaseBean;
import com.c9mj.platform.util.retrofit.bean.MovieBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public interface HttpService {

    @POST("user")
    Observable<LiveBaseBean<String>> login(

    );

    @GET("top250")
    Call<MovieBean> getMovie(
            @Query("start") int start,
            @Query("count") int count
    );
}
