package com.c9mj.platform.live.mvp.model;

/**
 * author: LMJ
 * date: 2016/9/12
 * 直播列表的ItemBean
 */
public class LiveListItemBean {

    /**
     * enable : 1
     * game_type : dota2
     * live_id : 246195
     * live_img : https://rpic.douyucdn.cn/z1609/19/15/246195_160919152708.jpg
     * live_name : douyu
     * live_nickname : 叶子长青K
     * live_online : 41
     * live_title : 叶中天 品火猫 感冒我需要打稳重点
     * live_type : douyu
     * live_userimg : http://uc.douyutv.com/avatar.php?uid=2350097&size=small
     * offline_time : 1474226479.5676
     * online_time : 1474207228.6536
     * push_time : 1474129572.6225
     * show_type : native
     * sort_num : 41
     * url_info : {"Referer":"http://api.douyutv.com/","User_Agent":"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.54","url":"http://capi.douyucdn.cn/api/v1/room/246195?aid=dotamax&time=1474270860&auth=d5cf3a2479f1b17fd7631adddf41cde7"}
     */

    private int enable;
    private String game_type;
    private String live_id;
    private String live_img;
    private String live_name;
    private String live_nickname;
    private int live_online;
    private String live_title;
    private String live_type;
    private String live_userimg;
    private String offline_time;
    private String online_time;
    private String push_time;
    private String show_type;
    private int sort_num;
    /**
     * Referer : http://api.douyutv.com/
     * User_Agent : Mozilla/5.0 (iPhone; CPU iPhone OS 7_0 like Mac OS X; en-us) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.54
     * url : http://capi.douyucdn.cn/api/v1/room/246195?aid=dotamax&time=1474270860&auth=d5cf3a2479f1b17fd7631adddf41cde7
     */

    private UrlInfoBean url_info;

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getGame_type() {
        return game_type;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    public String getLive_id() {
        return live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }

    public String getLive_img() {
        return live_img;
    }

    public void setLive_img(String live_img) {
        this.live_img = live_img;
    }

    public String getLive_name() {
        return live_name;
    }

    public void setLive_name(String live_name) {
        this.live_name = live_name;
    }

    public String getLive_nickname() {
        return live_nickname;
    }

    public void setLive_nickname(String live_nickname) {
        this.live_nickname = live_nickname;
    }

    public int getLive_online() {
        return live_online;
    }

    public void setLive_online(int live_online) {
        this.live_online = live_online;
    }

    public String getLive_title() {
        return live_title;
    }

    public void setLive_title(String live_title) {
        this.live_title = live_title;
    }

    public String getLive_type() {
        return live_type;
    }

    public void setLive_type(String live_type) {
        this.live_type = live_type;
    }

    public String getLive_userimg() {
        return live_userimg;
    }

    public void setLive_userimg(String live_userimg) {
        this.live_userimg = live_userimg;
    }

    public String getOffline_time() {
        return offline_time;
    }

    public void setOffline_time(String offline_time) {
        this.offline_time = offline_time;
    }

    public String getOnline_time() {
        return online_time;
    }

    public void setOnline_time(String online_time) {
        this.online_time = online_time;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public int getSort_num() {
        return sort_num;
    }

    public void setSort_num(int sort_num) {
        this.sort_num = sort_num;
    }

    public UrlInfoBean getUrl_info() {
        return url_info;
    }

    public void setUrl_info(UrlInfoBean url_info) {
        this.url_info = url_info;
    }

    public static class UrlInfoBean {
        private String Referer;
        private String User_Agent;
        private String url;

        public String getReferer() {
            return Referer;
        }

        public void setReferer(String Referer) {
            this.Referer = Referer;
        }

        public String getUser_Agent() {
            return User_Agent;
        }

        public void setUser_Agent(String User_Agent) {
            this.User_Agent = User_Agent;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
