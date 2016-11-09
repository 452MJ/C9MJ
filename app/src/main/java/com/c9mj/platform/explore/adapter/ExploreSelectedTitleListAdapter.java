package com.c9mj.platform.explore.adapter;

import android.support.v7.widget.CardView;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreSelectedTitleListAdapter extends BaseItemDraggableAdapter<String, BaseViewHolder> {
    public ExploreSelectedTitleListAdapter(List data) {
        super(R.layout.item_explore_title_list_layout, data);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, String title) {
        viewHolder.setText(R.id.explore_tv_title, title)
                .setTextColor(R.id.explore_tv_title, mContext.getResources().getColor(R.color.color_icons))
                .addOnClickListener(R.id.explore_cardview);
        CardView cardView = (CardView) viewHolder.getView(R.id.explore_cardview);
        cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.color_primary));
    }
}
