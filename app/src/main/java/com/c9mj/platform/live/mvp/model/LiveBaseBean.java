package com.c9mj.platform.live.mvp.model;

/**
 * author: LMJ
 * date: 2016/9/1
 * 网络请求实体基类
 */
public class LiveBaseBean<T> {

    /**
     * status : 0
     * message : 成功
     * result : {}
     */

    private String status;
    private String msg;
    private T result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
