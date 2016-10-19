package com.c9mj.platform.live.mvp.model.bean;

/**
 * Created by Administrator on 2016/10/19.
 */

public class DanmuBean {

    /**
     * type : 1
     * time : 1456824617
     * data : {"from":{"identity":"30","rid":"27742018","__plat":"pc_web","nickName":"neucrack","level":"2","userName":"PandaTv27742018"},"to":{"toqid":1,"toroom":"313180"},"content":"55555"}
     */

    private String type;
    private int time;
    /**
     * from : {"identity":"30","rid":"27742018","__plat":"pc_web","nickName":"neucrack","level":"2","userName":"PandaTv27742018"}
     * to : {"toqid":1,"toroom":"313180"}
     * content : 55555
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
         * identity : 30
         * rid : 27742018
         * __plat : android
         * nickName : neucrack
         * level : 2
         * userName : PandaTv27742018
         */

        private FromBean from;
        /**
         * toqid : 1
         * toroom : 313180
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
            private String identity;
            private String rid;
            private String __plat;
            private String nickName;
            private String level;
            private String userName;

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public String getRid() {
                return rid;
            }

            public void setRid(String rid) {
                this.rid = rid;
            }

            public String get__plat() {
                return __plat;
            }

            public void set__plat(String __plat) {
                this.__plat = __plat;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class ToBean {
            private int toqid;
            private String toroom;

            public int getToqid() {
                return toqid;
            }

            public void setToqid(int toqid) {
                this.toqid = toqid;
            }

            public String getToroom() {
                return toroom;
            }

            public void setToroom(String toroom) {
                this.toroom = toroom;
            }
        }
    }
}
