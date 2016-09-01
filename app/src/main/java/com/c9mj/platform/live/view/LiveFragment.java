package com.c9mj.platform.live.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.widget.TopBar;
import com.c9mj.platform.widget.TopBarConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class LiveFragment extends SupportFragment {

    @BindView(R.id.top_bar)
    TopBar topBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.bind(this, view);

        initTopBar();
        return view;
    }

    private void initTopBar() {
        TopBarConfig config = new TopBarConfig.Buider()
                .setTitleTextViewVisible(true)
                .setTitleTextViewText(getString(R.string.title_live))
                .create();
        topBar.init(config);
    }
}
