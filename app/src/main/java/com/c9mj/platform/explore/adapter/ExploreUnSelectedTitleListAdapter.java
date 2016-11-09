package com.c9mj.platform.explore.adapter;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreUnSelectedTitleListAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    public ExploreUnSelectedTitleListAdapter(List data) {
        super(R.layout.item_explore_title_list_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, String title) {
        viewHolder.setText(R.id.explore_tv_title, title)
                .addOnClickListener(R.id.explore_cardview);
    }
}
