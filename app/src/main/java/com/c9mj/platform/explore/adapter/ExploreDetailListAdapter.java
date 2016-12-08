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

public class ExploreDetailListAdapter extends BaseQuickAdapter<ExploreDetailBean.DetailBean.TopiclistNewsBean, BaseViewHolder> {


    public ExploreDetailListAdapter(List<ExploreDetailBean.DetailBean.TopiclistNewsBean> data) {
        super(R.layout.item_demo_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ExploreDetailBean.DetailBean.TopiclistNewsBean bean) {

    }
}
