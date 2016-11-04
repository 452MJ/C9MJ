package com.c9mj.platform.explore.adapter;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreSectionAdapter extends BaseItemDraggableAdapter {
    public ExploreSectionAdapter(List data) {
        super(R.layout.item_live_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Object o) {

    }
}
