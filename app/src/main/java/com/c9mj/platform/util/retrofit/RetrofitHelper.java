package com.c9mj.platform.util.retrofit;

import com.c9mj.platform.live.bean.LiveBaseBean;
import com.c9mj.platform.util.retrofit.exception.RetrofitException;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class RetrofitHelper{

    public static String BASE_EXPLORE_URL = "http://api.douban.com/v2/movie/";
    public static String BASE_LIVE_URL = "http://capi.douyucdn.cn";
    public static String BASE_USER_URL = "http://api.douban.com/v2/movie/";

    private static Retrofit retrofit = null;

    public static Retrofit getExploreHelper() {
        if (retrofit == null){
            synchronized (RetrofitHelper.class){
                retrofit = new Retrofit.Builder()
                        .client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build())
                        .baseUrl(BASE_EXPLORE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    public static Retrofit getLiveHelper() {
        if (retrofit == null){
            synchronized (RetrofitHelper.class){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_LIVE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    public static Retrofit getUserHelper() {
        if (retrofit == null){
            synchronized (RetrofitHelper.class){
                retrofit = new Retrofit.Builder()
                        .client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build())
                        .baseUrl(BASE_USER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    public static <T> Observable.Transformer<LiveBaseBean<T>, T> handleLiveResult(){
        return new Observable.Transformer<LiveBaseBean<T>, T>() {//被观察者：XXBasrBean<T> --> T
            @Override
            public Observable<T> call(Observable<LiveBaseBean<T>> baseBeanObservable) {//Step 1：获取Observable<XXBaseBean<T>>
                return baseBeanObservable.flatMap(new Func1<LiveBaseBean<T>, Observable<T>>() {//Step 2：把Observable<XXBaseBean<T>>转换为Observable<T>
                    @Override
                    public Observable<T> call(final LiveBaseBean<T> baseBean) {//Step 3:根据返回码决定是否发送事件
                        if (baseBean.getError() == 0){// 0：成功
                            return Observable.create(new Observable.OnSubscribe<T>() {
                                @Override
                                public void call(Subscriber<? super T> subscriber) {
                                    try {
                                        subscriber.onNext(baseBean.getData());//发送事件给Subscriber
                                        subscriber.onCompleted();
                                    }catch (Exception e){
                                        subscriber.onError(e);
                                    }
                                }
                            });
                        }else {//error:错误Exception
                            return Observable.error(new RetrofitException(baseBean.getError()));
                        }

                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
