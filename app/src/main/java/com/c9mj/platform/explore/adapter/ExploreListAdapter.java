package com.c9mj.platform.explore.adapter;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.explore.mvp.model.ExploreListItemBean;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.c9mj.platform.R.id.imageView;

/**
 * Created by Administrator on 2016/11/4.
 */

public class ExploreListAdapter extends BaseMultiItemQuickAdapter<ExploreListItemBean, BaseViewHolder> {
    public ExploreListAdapter(List data) {
        super(data);
        addItemType(ExploreListItemBean.ADS, R.layout.item_explore_list_ads_layout);
        addItemType(ExploreListItemBean.NORMAL, R.layout.item_explore_list_normal_layout);
    }


    @Override
    protected void convert(BaseViewHolder viewHolder, ExploreListItemBean bean) {
        switch (viewHolder.getItemViewType()) {
            case ExploreListItemBean.ADS:
                List<View> viewList = new ArrayList<>();
                ImageView iv_head = new ImageView(mContext);
                Glide.with(mContext)
                        .load(bean.getImgsrc())
                        .crossFade()
                        .centerCrop()
                        .into(iv_head);
                viewList.add(iv_head);
                for (ExploreListItemBean.AdsBean ads : bean.getAds()) {
                    ImageView iv_ads = new ImageView(mContext);
                    Glide.with(mContext)
                            .load(ads.getImgsrc())
                            .crossFade()
                            .centerCrop()
                            .into(iv_ads);
                    viewList.add(iv_ads);
                }

                ViewPager viewPager = viewHolder.getView(R.id.viewpager);
                ExploreAdsAdapter pageAdapter = new ExploreAdsAdapter(viewList);
                viewPager.setOffscreenPageLimit(4);
                viewPager.setAdapter(pageAdapter);

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
