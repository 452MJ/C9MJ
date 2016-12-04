package com.c9mj.platform.explore.adapter;

import android.support.v7.widget.CardView;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

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
        viewHolder.setText(R.id.tv_title, title)
                .setTextColor(R.id.tv_title, mContext.getResources().getColor(R.color.color_icons))
                .addOnClickListener(R.id.cardview);
        CardView cardView = (CardView) viewHolder.getView(R.id.cardview);
        cardView.setCardBackgroundColor(mContext.getResources().getColor(R.color.color_primary));
    }
}
