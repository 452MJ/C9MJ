package com.c9mj.platform.live.mvp.model;

/**
 * Created by Administrator on 2016/10/19.
 */

public class DanmuBean {


    /**
     * type : 1
     * time : 1477356473
     * data : {"from":{"__plat":"pc_web","identity":"30","level":"5","msgcolor":"","nickName":"个人昵称","rid":"23326050","sp_identity":"0","userName":""},"to":{"toroom":"15161"},"content":"英雄联盟"}
     */

    private String type;
    private int time;
    /**
     * from : {"__plat":"pc_web","identity":"30","level":"5","msgcolor":"","nickName":"个人昵称","rid":"23326050","sp_identity":"0","userName":""}
     * to : {"toroom":"15161"}
     * content : 英雄联盟
     */

    private DataBean data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * __plat : pc_web
         * identity : 30
         * level : 5
         * msgcolor :
         * nickName : 个人昵称
         * rid : 23326050
         * sp_identity : 0
         * userName :
         */

        private FromBean from;
        /**
         * toroom : 15161
         */

        private ToBean to;
        private String content;

        public FromBean getFrom() {
            return from;
        }

        public void setFrom(FromBean from) {
            this.from = from;
        }

        public ToBean getTo() {
            return to;
        }

        public void setTo(ToBean to) {
            this.to = to;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public static class FromBean {
            private String __plat;
            private String identity;
            private String level;
            private String msgcolor;
            private String nickName;
            private String rid;
            private String sp_identity;
            private String userName;

            public String get__plat() {
                return __plat;
            }

            public void set__plat(String __plat) {
                this.__plat = __plat;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getMsgcolor() {
                return msgcolor;
            }

            public void setMsgcolor(String msgcolor) {
                this.msgcolor = msgcolor;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String getSp_identity() {
                return sp_identity;
            }

            public void setSp_identity(String sp_identity) {
                this.sp_identity = sp_identity;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class ToBean {
            private String toroom;

            public String getToroom() {
                return toroom;
            }

            public void setToroom(String toroom) {
                this.toroom = toroom;
            }
        }
    }
}
