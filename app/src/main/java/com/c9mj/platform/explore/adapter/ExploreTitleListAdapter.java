package com.c9mj.platform.explore.adapter;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreTitleListAdapter extends BaseItemDraggableAdapter<String> {
    public ExploreTitleListAdapter(List data) {
        super(R.layout.item_explore_title_list_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, String title) {
        viewHolder.setText(R.id.explore_tv_title, title)
                .setOnClickListener(R.id.explore_cardview, new OnItemChildClickListener());
    }
}
