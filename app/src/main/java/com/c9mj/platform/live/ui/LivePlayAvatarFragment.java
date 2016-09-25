package com.c9mj.platform.live.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.widget.fragment.LazyFragment;

import static com.c9mj.platform.live.ui.LivePlayActivity.GAME_TYPE;

/**
 * author: LMJ
 * date: 2016/9/24
 * 观看直播：主播资料
 */
public class LivePlayAvatarFragment extends LazyFragment {

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
        return inflater.inflate(R.layout.fragment_live_play_chat, container, false);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }
}
