package com.c9mj.platform.live.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.live.bean.LiveRoomBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/12
 * 直播房间列表的Adapter
 */
public class LiveRoomAdapter extends BaseQuickAdapter<LiveRoomBean>{
    public LiveRoomAdapter(List<LiveRoomBean> data) {
        super(R.layout.item_live_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, LiveRoomBean bean) {
        viewHolder.setText(R.id.live_tv_roomname, bean.getRoom_name())//房间名称
                .setText(R.id.live_tv_nickname, bean.getNickname())//主播昵称
                .setText(R.id.live_tv_online, String.valueOf(bean.getOnline()));//在线人数
        Glide.with(mContext)//直播房间截图
                .load(bean.getRoom_src())
                .crossFade()
                .fitCenter()
                .into((ImageView) viewHolder.getView(R.id.live_iv_roomsrc));
        Glide.with(mContext)//主播头像
                .load(bean.getAvatar_mid())
                .crossFade()
                .centerCrop()
                .into((ImageView) viewHolder.getView(R.id.live_iv_avatar));
    }
}
