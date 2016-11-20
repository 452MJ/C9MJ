package com.c9mj.platform.live.adapter;

import android.text.TextUtils;

import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.model.DanmuBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import static android.R.attr.name;

/**
 * author: LMJ
 * date: 2016/9/12
 * 直播平台选择对话框
 */
public class LiveTypeAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
    public LiveTypeAdapter(List<String> data) {
        super(R.layout.item_live_type_picker_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String platform) {
        viewHolder.setText(R.id.chat_tv_nickname, platform)//昵称
                .setTextColor(R.id.live_type_picker_tv_platform, mContext.getResources().getColor(R.color.color_secondary_text));
    }
}
