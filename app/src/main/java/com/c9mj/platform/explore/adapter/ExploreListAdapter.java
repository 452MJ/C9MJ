package com.c9mj.platform.explore.adapter;

import com.c9mj.platform.R;
import com.c9mj.platform.explore.mvp.model.ExploreListItemBean;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreListAdapter extends BaseItemDraggableAdapter<ExploreListItemBean, BaseViewHolder> {
    public ExploreListAdapter(List data) {
        super(R.layout.item_explore_title_list_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, ExploreListItemBean bean) {
    }
}
