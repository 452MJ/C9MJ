package com.c9mj.platform.live.bean;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class LiveBaseBean<T> {

    /**
     * error : 0
     * message : 成功
     * data : {}
     */

    private int error;
    private T data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
