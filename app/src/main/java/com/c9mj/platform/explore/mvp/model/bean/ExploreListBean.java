package com.c9mj.platform.explore.mvp.model.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/1.
 */

public class ExploreListBean {

    private Map<String, List<ExploreListItemBean>> newslistitem;

    public Map<String, List<ExploreListItemBean>> getNewsListItem() {
        return newslistitem;
    }

    public void setNewsListItem(Map<String, List<ExploreListItemBean>> newslistitem) {
        this.newslistitem = newslistitem;
    }
}
