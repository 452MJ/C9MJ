package com.c9mj.platform.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailDouyuBean;
import com.c9mj.platform.live.mvp.presenter.impl.LivePlayPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILivePlayActivity;
import com.c9mj.platform.util.ToastUtil;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * author: LMJ
 * date: 2016/9/12
 * 观看直播Activity
 */
public class LivePlayActivity extends SwipeBackActivity implements ILivePlayActivity{

    public static final String LIVE_TYPE = "live_type"; //直播平台
    public static final String LIVE_ID = "live_id";     //直播房间ID
    public static final String GAME_TYPE = "game_type"; //直播游戏类型
    public static final String DOUYU_URL = "douyu_url"; //斗鱼直播url

    private String live_type;   //直播平台
    private String live_id;     //直播房间号ID
    private String game_type;   //直播游戏类型
    private String douyu_url;   //斗鱼直播专属url

    private Context context;
    private LivePlayPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play);
        ButterKnife.bind(this);

        context = this;

        //得到传入的参数
        Intent intent = getIntent();
        live_type = intent.getStringExtra(LIVE_TYPE);
        live_id = intent.getStringExtra(LIVE_ID);
        game_type = intent.getStringExtra(GAME_TYPE);
        douyu_url = intent.getStringExtra(DOUYU_URL);

        initMVP();
        initVideoView();

        presenter.getLiveDetail(live_type, live_id, game_type);     //请求直播详情
        presenter.getDanmuDetail(douyu_url);                          //请求弹幕服务器相关参数
    }

    private void initMVP() {
        presenter = new LivePlayPresenterImpl(context, this);
    }

    private void initVideoView() {

    }


    @Override
    public void updateLiveDetail(LiveDetailBean detailBean) {

    }

    @Override
    public void updateDouyuDetail(LiveDetailDouyuBean detailDouyuBean) {

    }

    @Override
    public void showError(String message) {
        ToastUtil.show(message);
    }
}
