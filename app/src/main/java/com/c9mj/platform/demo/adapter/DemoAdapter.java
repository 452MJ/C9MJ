package com.c9mj.platform.demo.adapter;

import com.c9mj.platform.R;
import com.c9mj.platform.demo.mvp.model.bean.DemoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */

public class DemoAdapter extends BaseQuickAdapter<DemoBean, BaseViewHolder> {


    public DemoAdapter(List<DemoBean> data) {
        super(R.layout.item_demo_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, DemoBean bean) {

    }
}
