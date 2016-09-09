package com.c9mj.platform.live.bean;

/**
 * author: LMJ
 * date: 2016/9/9
 * 用于顶部Indicator
 */
public class LiveIndicatorBean {
    /**
     * cate_id : 1
     * cate_name : 热门游戏
     * short_name : game
     * push_ios : 1
     * push_show : 0
     * push_vertical_screen : 0
     */

    private String cate_id;
    private String cate_name;
    private String short_name;
    private String push_ios;
    private String push_show;
    private String push_vertical_screen;

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getPush_ios() {
        return push_ios;
    }

    public void setPush_ios(String push_ios) {
        this.push_ios = push_ios;
    }

    public String getPush_show() {
        return push_show;
    }

    public void setPush_show(String push_show) {
        this.push_show = push_show;
    }

    public String getPush_vertical_screen() {
        return push_vertical_screen;
    }

    public void setPush_vertical_screen(String push_vertical_screen) {
        this.push_vertical_screen = push_vertical_screen;
    }
}
