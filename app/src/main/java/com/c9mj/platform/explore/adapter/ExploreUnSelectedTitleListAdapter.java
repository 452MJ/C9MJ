package com.c9mj.platform.explore.adapter;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreUnSelectedTitleListAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    public ExploreUnSelectedTitleListAdapter(List<String> data) {
        super(R.layout.item_explore_title_list_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, String title) {
        viewHolder.setText(R.id.tv_title, title)
                .addOnClickListener(R.id.cardview);
    }
}
