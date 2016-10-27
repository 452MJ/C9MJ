package com.c9mj.platform.live.ui;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.presenter.impl.LiveListPresenterImpl;
import com.c9mj.platform.widget.fragment.LazyFragment;

import butterknife.ButterKnife;

/**
 * author: LMJ
 * date: 2016/10/27
 * 直播播放页面（主播资料）
 */
public class LivePlayAvatarFragment extends LazyFragment {

    private static final String GAME_TYPE = "game_type";

    private Context context;
    private LiveListPresenterImpl presenter;

    public static LivePlayAvatarFragment newInstance() {
        return newInstance("");
    }

    public static LivePlayAvatarFragment newInstance(String game_type) {
        LivePlayAvatarFragment fragment = new LivePlayAvatarFragment();
        Bundle args = new Bundle();
        args.putString(GAME_TYPE, game_type);
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
        }
    }

}
