package com.c9mj.platform.explore.adapter;

import com.c9mj.platform.R;
import com.c9mj.platform.demo.mvp.model.bean.DemoBean;
import com.c9mj.platform.explore.mvp.model.bean.ExploreDetailBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ExploreDetailTopicListAdapter extends BaseQuickAdapter<ExploreDetailBean.RelativeSysBean, BaseViewHolder> {


    public ExploreDetailTopicListAdapter(List<ExploreDetailBean.RelativeSysBean> data) {
        super(R.layout.item_explore_list_topic_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ExploreDetailBean.RelativeSysBean bean) {

    }
}
