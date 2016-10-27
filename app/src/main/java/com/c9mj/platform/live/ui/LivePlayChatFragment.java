package com.c9mj.platform.live.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.live.adapter.LiveListAdapter;
import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.bean.LiveListItemBean;
import com.c9mj.platform.live.mvp.presenter.impl.LiveListPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILiveListFragment;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.widget.animation.CustionAnimation;
import com.c9mj.platform.widget.fragment.LazyFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: LMJ
 * date: 2016/10/27
 * 直播播放页面（聊天室）
 */
public class LivePlayChatFragment extends LazyFragment {

    private static final String GAME_TYPE = "game_type";

    private Context context;
    private LiveListPresenterImpl presenter;

    public static LivePlayChatFragment newInstance() {
        return newInstance("");
    }

    public static LivePlayChatFragment newInstance(String game_type) {
        LivePlayChatFragment fragment = new LivePlayChatFragment();
        Bundle args = new Bundle();
        args.putString(GAME_TYPE, game_type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_play_chat_layout, container, false);
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
