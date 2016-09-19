package com.c9mj.platform.live.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.live.bean.LiveListItemBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * author: LMJ
 * date: 2016/9/12
 * 直播房间列表的Adapter
 */
public class LiveListAdapter extends BaseQuickAdapter<LiveListItemBean>{
    public LiveListAdapter(List<LiveListItemBean> data) {
        super(R.layout.item_live_list_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, LiveListItemBean bean) {
        viewHolder.setText(R.id.live_tv_roomname, bean.getLive_title())//房间名称
                .setText(R.id.live_tv_nickname, bean.getLive_nickname())//主播昵称
                .setText(R.id.live_tv_online, String.valueOf(bean.getLive_online()))//在线人数
                .setOnClickListener(R.id.live_cardview, new OnItemChildClickListener());//添加子Item点击监听，在UI中实现回调接口

        Glide.with(mContext)//直播房间截图
                .load(bean.getLive_img())
                .crossFade()
                .centerCrop()
                .into((ImageView) viewHolder.getView(R.id.live_iv_roomsrc));
        Glide.with(mContext)//主播头像
                .load(bean.getLive_userimg())
                .crossFade()
                .centerCrop()
                .placeholder(R.drawable.ic_avatar_default_40dp)
                .into((ImageView) viewHolder.getView(R.id.live_iv_avatar));
    }
}
