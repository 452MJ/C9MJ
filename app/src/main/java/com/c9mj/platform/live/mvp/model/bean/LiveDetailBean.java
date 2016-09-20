package com.c9mj.platform.live.mvp.model.bean;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/20
 * 直播详情
 */

public class LiveDetailBean {

    /**
     * enable : 1
     * game_type : lol
     * is_followed : 0
     * live_id : 453751
     * live_img : https://rpic.douyucdn.cn/z1609/20/09/453751_160920093735.jpg
     * live_name : douyu
     * live_nickname : 我叫撸管飞
     * live_online : 88739
     * live_title : 国服第一风暴诺手！五天大师！
     * live_type : douyu
     * live_userimg :
     * offline_time : 1473748293.7780
     * online_time : 1473735863.0744
     * push_time : 1473730708.5845
     * sort_num : 88739
     * stream_list : [{"type":"超清","url":"http://hdl3.douyutv.com/live/453751rmOQP0R7yZ.flv?wsSecret=2eeda296e6253d3d3e27632c44d9fbf8&wsTime=1454410265"},{"type":"普清","url":"http://hdl3.douyutv.com/live/453751rmOQP0R7yZ_550.flv?wsSecret=7341e36a7b32c22e6bc45135d1905867&wsTime=1454410265"}]
     */

    private int enable;
    private String game_type;
    private int is_followed;
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
    private int sort_num;
    /**
     * type : 超清
     * url : http://hdl3.douyutv.com/live/453751rmOQP0R7yZ.flv?wsSecret=2eeda296e6253d3d3e27632c44d9fbf8&wsTime=1454410265
     */

    private List<StreamListBean> stream_list;

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

    public int getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(int is_followed) {
        this.is_followed = is_followed;
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

    public int getSort_num() {
        return sort_num;
    }

    public void setSort_num(int sort_num) {
        this.sort_num = sort_num;
    }

    public List<StreamListBean> getStream_list() {
        return stream_list;
    }

    public void setStream_list(List<StreamListBean> stream_list) {
        this.stream_list = stream_list;
    }

    public static class StreamListBean {
        private String type;
        private String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
