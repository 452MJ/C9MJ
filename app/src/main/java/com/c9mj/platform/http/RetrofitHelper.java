package com.c9mj.platform.http;

import com.example.administrator.every_sample.bean.BaseBean;

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
 * Created by Administrator on 2016/8/24.
 */
public class RetrofitHelper{

    public static String BASE_URL = "http://api.douban.com/v2/movie/";

    private static Retrofit retrofit = null;

    public static Retrofit getInstance() {
        if (retrofit == null){
            synchronized (RetrofitHelper.class){
                retrofit = new Retrofit.Builder()
                        .client(new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build())
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    public static Retrofit getInstance(String baseUrl) {
        if (retrofit == null){
            synchronized (RetrofitHelper.class){
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    public static Retrofit getDefault(String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit;
    }

    public static <T> Observable.Transformer<BaseBean<T>, T> handleResult(){
        return new Observable.Transformer<BaseBean<T>, T>() {//被观察者：BasrBean<T> --> T
            @Override
            public Observable<T> call(Observable<BaseBean<T>> baseBeanObservable) {//Step 1：获取Observable<BaseBean<T>>
                return baseBeanObservable.flatMap(new Func1<BaseBean<T>, Observable<T>>() {//Step 2：把Observable<BaseBean<T>>转换为Observable<T>
                    @Override
                    public Observable<T> call(final BaseBean<T> baseBean) {//Step 3:根据返回码决定是否发送事件
                        if (baseBean.getCode() == 0){// 0：成功
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
                        }else {
                            return Observable.error(new RetrofitException(baseBean.getCode()));
                        }

                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
