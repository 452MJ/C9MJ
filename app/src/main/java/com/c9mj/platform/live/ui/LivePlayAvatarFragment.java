package com.c9mj.platform.live.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.model.LiveDetailBean;
import com.c9mj.platform.widget.fragment.LazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: LMJ
 * date: 2016/10/27
 * 直播播放页面（主播资料）
 */
public class LivePlayAvatarFragment extends LazyFragment {

    private static final String AVATAR = "avatar";

    @BindView(R.id.live_play_avatar_tv_name)
    TextView livePlayAvatarTvName;
    @BindView(R.id.live_play_avatar_tv_live_type)
    TextView livePlayAvatarTvLiveType;
    @BindView(R.id.live_play_avatar_tv_game_type)
    TextView livePlayAvatarTvGameType;
    @BindView(R.id.live_play_avatar_iv_backbround)
    ImageView livePlayAvatarIvBackbround;

    private Context context;

    public static LivePlayAvatarFragment newInstance() {
        return newInstance("");
    }

    public static LivePlayAvatarFragment newInstance(String game_type) {
        LivePlayAvatarFragment fragment = new LivePlayAvatarFragment();
        Bundle args = new Bundle();
        args.putString(AVATAR, game_type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_play_avatar_layout, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {

        }
    }

    public void updateLiveDetail(LiveDetailBean detailBean) {
        livePlayAvatarTvName.setText(detailBean.getLive_nickname());
        livePlayAvatarTvLiveType.setText(detailBean.getLive_type());
        livePlayAvatarTvGameType.setText(detailBean.getGame_type());

        String imgUrl = detailBean.getLive_userimg();
        if (!TextUtils.isEmpty(imgUrl)){

            Glide.with(context)//主播头像
                    .load(imgUrl)
                    .crossFade()
                    .centerCrop()
                    .thumbnail(0.1f)
                    .into(livePlayAvatarIvBackbround);
        }
    }

}
