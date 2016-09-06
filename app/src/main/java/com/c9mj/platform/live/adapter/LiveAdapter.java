package com.c9mj.platform.live.adapter;

import com.c9mj.platform.R;
import com.c9mj.platform.live.bean.LiveBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/6
 */
public class LiveAdapter extends BaseQuickAdapter<LiveBean>{
    public LiveAdapter(List<LiveBean> data) {
        super(R.layout.item_live_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LiveBean liveBean) {

    }
}
