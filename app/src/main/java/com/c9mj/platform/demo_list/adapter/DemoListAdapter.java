package com.c9mj.platform.demo_list.adapter;

import com.c9mj.platform.R;
import com.c9mj.platform.demo_list.mvp.model.bean.DemoListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;


public class DemoListAdapter extends BaseQuickAdapter<DemoListBean, BaseViewHolder> {


    public DemoListAdapter(List<DemoListBean> data) {
        super(R.layout.item_demo_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DemoListBean bean) {

    }
}
