package com.c9mj.platform.explore.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.explore.mvp.model.ExploreListItemBean;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreListAdapter extends BaseMultiItemQuickAdapter<ExploreListItemBean, BaseViewHolder> {
    public ExploreListAdapter(List data) {
        super(data);
        addItemType(ExploreListItemBean.ADS, R.layout.item_explore_list_normal_layout);
        addItemType(ExploreListItemBean.NORMAL, R.layout.item_explore_list_normal_layout);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, ExploreListItemBean bean) {
        switch (viewHolder.getItemViewType()) {
            case ExploreListItemBean.ADS:
                viewHolder.setText(R.id.tv_title, bean.getTitle())
                        .setText(R.id.tv_source, bean.getSource());
                Glide.with(mContext)
                        .load(bean.getImgsrc())
                        .crossFade()
                        .centerCrop()
                        .into((ImageView) viewHolder.getView(R.id.iv_img));
                break;
            case ExploreListItemBean.NORMAL:
                viewHolder.setText(R.id.tv_title, bean.getTitle())
                        .setText(R.id.tv_source, bean.getSource());
                Glide.with(mContext)
                        .load(bean.getImgsrc())
                        .crossFade()
                        .centerCrop()
                        .into((ImageView) viewHolder.getView(R.id.iv_img));
                break;
        }
    }
}
