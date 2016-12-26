package com.c9mj.platform.live.adapter;

import android.support.v4.content.ContextCompat;

import com.c9mj.platform.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/12
 * 直播平台选择对话框
 */
public class LiveTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public LiveTypeAdapter(List<String> data) {
        super(R.layout.item_live_type_picker_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String platform) {
        viewHolder.setText(R.id.tv_platform, platform)//昵称
                .setTextColor(R.id.tv_platform, ContextCompat.getColor(mContext, R.color.color_secondary_text));
    }
}
