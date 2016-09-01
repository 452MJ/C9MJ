package com.c9mj.platform.util.retrofit;

import rx.Subscriber;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public abstract class HttpSubscriber<T> extends Subscriber<T>{

    public abstract void _onNext(T t);
    public abstract void _onError(String message);

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        _onError(e.getMessage());
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }
}
