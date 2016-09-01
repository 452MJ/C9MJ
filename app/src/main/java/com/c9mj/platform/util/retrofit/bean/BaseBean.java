package com.c9mj.platform.util.retrofit.bean;

/**
 * Created by Administrator on 2016/8/23.
 */
public class BaseBean<T> {

    /**
     * code : 0
     * message : 成功
     * data : {}
     */

    private int code;
    private String message;
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
