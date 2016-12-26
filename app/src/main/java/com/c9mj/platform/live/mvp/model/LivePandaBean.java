package com.c9mj.platform.live.mvp.model;

import java.util.ArrayList;

/**
 * author: LMJ
 * date: 2016/9/20
 * 熊猫直播详情
 */

public class LivePandaBean {


    /**
     * errno : 0
     * errmsg :
     * data : {"appid":"134273394","rid":-21105662,"sign":"0fbbcc66cbeb1fb1e87f6abeec38df01","authType":"3","ts":1476770753000,"chat_addr_list":["54.222.146.133:443","54.222.175.228:443","54.223.58.164:443"]}
     */

    private int errno;
    private String errmsg;
    /**
     * appid : 134273394
     * rid : -21105662
     * sign : 0fbbcc66cbeb1fb1e87f6abeec38df01
     * authType : 3
     * ts : 1476770753000
     * chat_addr_list : ["54.222.146.133:443","54.222.175.228:443","54.223.58.164:443"]
     */

    private DataBean data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LivePandaBean{" +
                "data=" + data +
                '}';
    }

    public static class DataBean {
        private String appid;
        private int rid;
        private String sign;
        private String authType;
        private long ts;
        private ArrayList<String> chat_addr_list;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAuthType() {
            return authType;
        }

        public void setAuthType(String authType) {
            this.authType = authType;
        }

        public long getTs() {
            return ts;
        }

        public void setTs(long ts) {
            this.ts = ts;
        }

        public ArrayList<String> getChat_addr_list() {
            return chat_addr_list;
        }

        public void setChat_addr_list(ArrayList<String> chat_addr_list) {
            this.chat_addr_list = chat_addr_list;
        }
    }
}
