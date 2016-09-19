package com.c9mj.platform.live.ui;

import android.os.Bundle;
import android.widget.Button;

import com.c9mj.platform.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * author: LMJ
 * date: 2016/9/12
 * 观看直播Activity
 */
public class LivePlayActivity extends SwipeBackActivity {

    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onClick() {
    }

}
