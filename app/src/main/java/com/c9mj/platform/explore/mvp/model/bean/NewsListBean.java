package com.c9mj.platform.explore.mvp.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/1.
 */

public class NewsListBean {

    private Map<String, NewsListItemBean> newslistitem;

    public Map<String, NewsListItemBean> getNewsListItem() {
        return newslistitem;
    }

    public void setNewsListItem(Map<String, NewsListItemBean> newslistitem) {
        this.newslistitem = newslistitem;
    }
}
