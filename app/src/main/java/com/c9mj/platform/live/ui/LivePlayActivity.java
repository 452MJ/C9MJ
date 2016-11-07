package com.c9mj.platform.live.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.blankj.utilcode.utils.SizeUtils;
import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.model.bean.DanmuBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LivePandaBean;
import com.c9mj.platform.live.mvp.presenter.impl.LivePlayPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILivePlayActivity;
import com.c9mj.platform.util.SnackbarUtil;
import com.c9mj.platform.util.adapter.FragmentAdapter;
import com.c9mj.platform.util.retrofit.exception.MediaException;
import com.c9mj.platform.widget.activity.BaseActivity;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * author: LMJ
 * date: 2016/9/12
 * 观看直播Activity
 */
public class LivePlayActivity extends BaseActivity
        implements ILivePlayActivity,
        PLMediaPlayer.OnPreparedListener,
        PLMediaPlayer.OnVideoSizeChangedListener,
        PLMediaPlayer.OnCompletionListener,
        PLMediaPlayer.OnInfoListener,
        PLMediaPlayer.OnErrorListener,
        Handler.Callback {

    public static final String LIVE_TYPE = "live_type"; //直播平台
    public static final String LIVE_ID = "live_id";     //直播房间ID
    public static final String GAME_TYPE = "game_type"; //直播游戏类型

    public static final int HANDLER_HIDE_CONTROLLER = 100;//隐藏MediaController
    public static final int HANDLER_CONTROLLER_DURATION = 5 * 1000;//MediaController显示时间

    private boolean isSurfaceViewInit = false;         //SurfaceView初始化标志位
    private boolean isVideoPrepared = false;         //Video加载标志位，用于显示隐藏ProgreeBar
    private boolean isPause = false;         //直播暂停标志位
    private boolean isFullscreen = false;   //全屏标志位
    private boolean isControllerHiden = false;   //MediaController显示隐藏标志位

    private String live_type;   //直播平台
    private String live_id;     //直播房间号ID
    private String game_type;   //直播游戏类型
    private String live_url;   //直播url

    private int surfacePortraitWidth;
    private int surfacePortraitHeight;
    private int videoWidth;
    private int videoHeight;
    private int playWidth;
    private int playHeight;

    private List<LiveDetailBean.StreamListBean> streamList = new ArrayList<>();//直播流列表

    private Context context;
    private LivePlayPresenterImpl presenter;
    private Handler controllerHandler;

    @BindView(R.id.surfaceview)
    SurfaceView surfaceView;                  //用于显示播放画面
    private PLMediaPlayer mediaPlayer;  //媒体控制器
    private AVOptions avOptions;        //播放参数配置

    @BindView(R.id.danmuview)
    DanmakuView danmuView;
    private boolean isShowDanmu = false;// 弹幕显示标志位
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    //横屏控件
    @BindView(R.id.controller_landscape_iv_back)
    ImageView controllerLandscapeIvBack;
    @BindView(R.id.controller_landscape_tv_roomname)
    TextView controllerLandscapeTvRoomname;
    @BindView(R.id.controller_landscape_btn_stream_1080p)
    Button controllerLandscapeBtnStream1080P;
    @BindView(R.id.controller_landscape_btn_stream_360p)
    Button controllerLandscapeBtnStream360P;
    @BindView(R.id.controller_landscape_iv_play_pause)
    ImageView controllerLandscapeIvPlayPause;
    @BindView(R.id.controller_landscape_iv_refresh)
    ImageView controllerLandscapeIvRefresh;
    @BindView(R.id.controller_landscape_et_danmu)
    EditText controllerLandscapeEtDanmu;
    @BindView(R.id.controller_landscape_btn_senddanmu)
    Button controllerLandscapeBtnSenddanmu;
    @BindView(R.id.controller_landscape_iv_danmu_visible)
    ImageView controllerLandscapeIvDanmuVisible;
    @BindView(R.id.controller_landscape_iv_fullscreen_exit)
    ImageView controllerLandscapeIvFullscreenExit;
    @BindView(R.id.controller_landscape_layout)
    RelativeLayout controllerLandscapeLayout;
    //竖屏控件
    @BindView(R.id.controller_portrait_iv_back)
    ImageView controllerPortraitIvBack;
    @BindView(R.id.controller_portrait_iv_danmu_visible)
    ImageView controllerPortraitIvDanmuVisible;
    @BindView(R.id.controller_portrait_iv_fullscreen)
    ImageView controllerPortraitIvFullscreen;
    @BindView(R.id.controller_portrait_layout)
    RelativeLayout controllerPortraitLayout;

    @BindView(R.id.live_play_progressbar)
    FrameLayout livePlayProgreeBar;
    @BindView(R.id.live_play_top_layout)
    FrameLayout livePlayTopLayout;

    //底部Layout相关
    @BindView(R.id.live_play_bottom_layout)
    LinearLayout livePlayBottomLayout;
    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
    CommonNavigatorAdapter navigatorAdapter;
    List<String> titleList = new ArrayList<>();
    final String[] indicatorText = new String[]{
            "聊天",
            "主播"
    };
    final int[] normalResId = new int[]{
            R.drawable.ic_danmu_on_normal_dark,
            R.drawable.ic_avatar_normal
    };
    final int[] pressedResId = new int[]{
            R.drawable.ic_danmu_on_pressed,
            R.drawable.ic_avatar_pressed
    };

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    LivePlayChatFragment chatFragment;//弹幕聊天室Fragment
    LivePlayAvatarFragment avatarFragment;//主播详情Fragment
    List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_play);
        ButterKnife.bind(this);

        context = this;

        controllerHandler = new Handler(this);

        //得到传入的参数
        Intent intent = getIntent();
        live_type = intent.getStringExtra(LIVE_TYPE);
        live_id = intent.getStringExtra(LIVE_ID);
        game_type = intent.getStringExtra(GAME_TYPE);

        initMVP();
        initSurfaceView();
        initController();
        initDanmuView();
        initViewPager();
        initIndicator();

        presenter.getLiveDetail(live_type, live_id, game_type);     //请求直播详情
        presenter.getDanmuDetail(live_id, live_type);                          //请求弹幕服务器相关参数
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (mediaPlayer != null && isPause == true && !TextUtils.isEmpty(live_url)) {
            try {
//                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                mediaPlayer.reset();
                mediaPlayer.setDataSource(live_url);
                mediaPlayer.prepareAsync();
                isPause = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (danmuView != null && danmuView.isPrepared() && danmuView.isPaused()) {
            danmuView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.abandonAudioFocus(null);
        }
        if (danmuView != null && danmuView.isPrepared()) {
            danmuView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.abandonAudioFocus(null);
        }
        //断开弹幕服务器连接
        presenter.closeConnection();

        //关闭弹幕
        isShowDanmu = false;
        if (danmuView != null) {
            danmuView.release();
            danmuView = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        livePlayProgreeBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onBackPressedSupport() {
        if (isFullscreen == true) {
            exitFullscreen();
        } else {
            super.onBackPressedSupport();
        }
    }

    private void initMVP() {
        presenter = new LivePlayPresenterImpl(context, this);
    }

    /**
     * 监听SurfaceView回调
     */
    private void initSurfaceView() {
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                livePlayProgreeBar.setVisibility(View.VISIBLE);
                prepareMediaPlayer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (isSurfaceViewInit == false) {
                    //竖屏
                    surfacePortraitWidth = width;
                    surfacePortraitHeight = height;

                    isSurfaceViewInit = true;
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mediaPlayer != null) {
                    mediaPlayer.setDisplay(null);
                }
            }
        });

    }

    /**
     * 初始化控制器
     */
    private void initController() {

        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        controllerLandscapeEtDanmu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //点击弹幕编辑时取消隐藏Controller
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                return false;
            }
        });

    }

    /**
     * 初始化弹幕引擎
     */
    private void initDanmuView() {

        controllerLandscapeIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
        controllerPortraitIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);

        danmuView.enableDanmakuDrawingCache(true);//打开绘图缓存，提升绘制效率
        danmuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                try {
                    isShowDanmu = true;
                    danmuView.start();
                    controllerLandscapeIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    controllerPortraitIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuContext.setDuplicateMergingEnabled(true);//设置合并重复弹幕
        danmuView.prepare(parser, danmakuContext);
    }

    private void initViewPager(){
        titleList.add(indicatorText[0]);
        titleList.add(indicatorText[1]);

        chatFragment = LivePlayChatFragment.newInstance();
        avatarFragment = LivePlayAvatarFragment.newInstance();

        fragmentList.add(chatFragment);
        fragmentList.add(avatarFragment);

        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);

    }

    private void initIndicator(){
        CommonNavigator navigator = new CommonNavigator(context);
        navigator.setAdjustMode(true);
        navigator.setFollowTouch(true);
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context);

                titleView.setContentView(R.layout.item_live_play_indicator_layout);//加载自定义布局作为Tab

                final LinearLayout live_play_indicator_layout = (LinearLayout) titleView.findViewById(R.id.live_play_indicator_layout);
                final ImageView live_play_iv_icon = (ImageView) titleView.findViewById(R.id.live_play_iv_icon);
                final TextView live_play_tv_title = (TextView) titleView.findViewById(R.id.live_play_tv_title);

                titleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        live_play_iv_icon.setImageResource(pressedResId[index]);
                        live_play_tv_title.setTextColor(getResources().getColor(R.color.color_primary));
                        live_play_tv_title.setText(indicatorText[index]);
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        live_play_iv_icon.setImageResource(normalResId[index]);
                        live_play_tv_title.setTextColor(getResources().getColor(R.color.color_secondary_text));
                        live_play_tv_title.setText(indicatorText[index]);
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });

                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setLineHeight(UIUtil.dip2px(context, 3));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
//                indicator.setYOffset(UIUtil.dip2px(context, 0.5));
                indicator.setColors(getResources().getColor(R.color.color_primary));
                return indicator;
            }
        };

        navigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(navigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }


    /**
     * 配置MediaPlayer相关参数
     */
    private void prepareMediaPlayer() {

        if (mediaPlayer != null) {
            mediaPlayer.setDisplay(surfaceView.getHolder());
            return;
        }

        try {
            avOptions = new AVOptions();
            avOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, 0);  //直播流：1->是 0->否
            avOptions.setInteger(AVOptions.KEY_MEDIACODEC, 0);      //解码类型 1->硬解 0->软解
            avOptions.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);//缓冲结束后自动播放
            avOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
            avOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
            avOptions.setInteger(AVOptions.KEY_BUFFER_TIME, 10 * 1000);
            avOptions.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
            avOptions.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 10 * 1000);
            avOptions.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 15 * 1000);

//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            mediaPlayer = new PLMediaPlayer(context, avOptions);

            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnErrorListener(this);

            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
//            mediaPlayer.setDataSource(live_url);
            mediaPlayer.setDisplay(surfaceView.getHolder());
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateLiveDetail(LiveDetailBean detailBean) {
        try {
            controllerLandscapeTvRoomname.setText(detailBean.getLive_title());
            streamList = detailBean.getStream_list();
            LiveDetailBean.StreamListBean stream = streamList.get(streamList.size() - 1);
            live_url = stream.getUrl();
            if (streamList.size() == 1) {
                if (stream.getType().equals(getString(R.string.stream_1080p))) {
                    controllerLandscapeBtnStream360P.setVisibility(View.GONE);
                }
                if (stream.getType().equals(getString(R.string.stream_360p))) {
                    controllerLandscapeBtnStream1080P.setVisibility(View.GONE);
                }
            }
            if (stream.getType().equals(getString(R.string.stream_1080p))) {
                controllerLandscapeBtnStream1080P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_pressed));
                controllerLandscapeBtnStream360P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_normal));
                controllerLandscapeBtnStream1080P.setTextColor(getResources().getColor(R.color.color_primary));
                controllerLandscapeBtnStream360P.setTextColor(getResources().getColor(R.color.color_icons));
            }
            if (stream.getType().equals(getString(R.string.stream_360p))) {
                controllerLandscapeBtnStream1080P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_normal));
                controllerLandscapeBtnStream360P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_pressed));
                controllerLandscapeBtnStream1080P.setTextColor(getResources().getColor(R.color.color_icons));
                controllerLandscapeBtnStream360P.setTextColor(getResources().getColor(R.color.color_primary));
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            live_url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        }
        try {
            mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把LiveDetail传入主播页面Fragment
        avatarFragment.updateLiveDetail(detailBean);
    }

    @Override
    public void updateChatDetail(LivePandaBean pandaBean) {
        presenter.connectToChatRoom(live_id, pandaBean);
    }

    @Override
    public void receiveDanmu(DanmuBean danmuBean, boolean withBorder) {
        this.addDanmuOnDanmakuView(danmuBean, false);
        if (chatFragment != null){
            chatFragment.addDanmuOnRecyclerView(danmuBean);
        }
    }

    @Override
    public void showError(String message) {
        SnackbarUtil.show(message);
    }


    /********
     * 以下实现的Interface都是MediaPlayer的监听
     *********/
    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer) {
        livePlayProgreeBar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
        mediaPlayer.start();
    }

    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height) {
        videoWidth = width;
        videoHeight = height;

        if (videoWidth != 0 && videoHeight != 0) {
            float ratioW = (float) videoWidth / (float) (isFullscreen ? ScreenUtils.getScreenWidth(context) : surfacePortraitWidth);
            float ratioH = (float) videoHeight / (float) (isFullscreen ? ScreenUtils.getScreenHeight(context) : surfacePortraitHeight);
            float ratio = Math.max(ratioW, ratioH);
            playWidth = (int) Math.ceil((float) videoWidth / ratio);
            playHeight = (int) Math.ceil((float) videoHeight / ratio);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(playWidth, playHeight);
            lp.gravity = Gravity.CENTER;
            surfaceView.setLayoutParams(lp);
        }
    }

    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
        switch (what) {
            case PLMediaPlayer.MEDIA_INFO_BUFFERING_START://开始缓冲
//                isVideoPrepared = false;
                Log.d("PLMediaPlayer", "onInfo: MEDIA_INFO_BUFFERING_START");
                break;
            case PLMediaPlayer.MEDIA_INFO_BUFFERING_END://缓冲结束
                Log.d("PLMediaPlayer", "onInfo: MEDIA_INFO_BUFFERING_END");
                break;
            case PLMediaPlayer.MEDIA_INFO_BUFFERING_BYTES_UPDATE:
                Log.d("PLMediaPlayer", "onInfo: MEDIA_INFO_BUFFERING_BYTES_UPDATE");
                break;
            case PLMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                Log.d("PLMediaPlayer", "onInfo: MEDIA_INFO_NOT_SEEKABLE");
                break;
            case PLMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                Log.d("PLMediaPlayer", "onInfo: MEDIA_INFO_VIDEO_ROTATION_CHANGED");
                break;
            case PLMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                Log.d("PLMediaPlayer", "onInfo: MEDIA_INFO_AUDIO_RENDERING_START");
                break;
            case PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START://视频缓冲完成可播放
                livePlayProgreeBar.setVisibility(View.GONE);
                isVideoPrepared = true;
                isPause = false;
                controllerLandscapeIvPlayPause.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
                Log.d("PLMediaPlayer", "onInfo: MEDIA_INFO_VIDEO_RENDERING_START");
                break;
            default:
                Log.d("PLMediaPlayer", "onInfo: " + what);
                break;
        }
        return true;
    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
        showError(new MediaException(errorCode).getMessage());
        return true;
    }

    @OnClick({
            R.id.surfaceview,
            R.id.controller_landscape_iv_back,
            R.id.controller_landscape_btn_stream_1080p,
            R.id.controller_landscape_btn_stream_360p,
            R.id.controller_landscape_iv_play_pause,
            R.id.controller_landscape_iv_refresh,
            R.id.controller_landscape_btn_senddanmu,
            R.id.controller_landscape_iv_danmu_visible,
            R.id.controller_landscape_iv_fullscreen_exit,
            R.id.controller_portrait_iv_back,
            R.id.controller_portrait_iv_danmu_visible,
            R.id.controller_portrait_iv_fullscreen
    })
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.surfaceview:
                if (isFullscreen == true) {
                    if (isControllerHiden == true) {//全屏&&隐藏
                        controllerLandscapeLayout.setVisibility(View.VISIBLE);
                        controllerPortraitLayout.setVisibility(View.GONE);
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                        isControllerHiden = false;
                    } else if (isControllerHiden == false) {//全屏&&显示
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessage(HANDLER_HIDE_CONTROLLER);
                    }
                } else if (isFullscreen == false) {
                    if (isControllerHiden == true) {//非全屏&&隐藏
                        controllerLandscapeLayout.setVisibility(View.GONE);
                        controllerPortraitLayout.setVisibility(View.VISIBLE);
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                        isControllerHiden = false;
                    } else if (isControllerHiden == false) {//非全屏&&显示
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessage(HANDLER_HIDE_CONTROLLER);
                    }
                }
                break;

            //全屏Back
            case R.id.controller_landscape_iv_back:
                onBackPressedSupport();
                break;

            //直播流连接切换（超清）
            case R.id.controller_landscape_btn_stream_1080p:
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                controllerLandscapeBtnStream1080P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_pressed));
                controllerLandscapeBtnStream360P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_normal));
                controllerLandscapeBtnStream1080P.setTextColor(getResources().getColor(R.color.color_primary));
                controllerLandscapeBtnStream360P.setTextColor(getResources().getColor(R.color.color_icons));

                for (LiveDetailBean.StreamListBean stream :
                        streamList) {
                    if (stream.getType().equals(getString(R.string.stream_1080p))) {
                        live_url = stream.getUrl();
                    }
                }
                mediaPlayer.reset();
                //显示ProgressBar
                isVideoPrepared = false;
                livePlayProgreeBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
                try {
                    mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.prepareAsync();
                break;

            //直播流连接切换（普清）
            case R.id.controller_landscape_btn_stream_360p:
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                controllerLandscapeBtnStream1080P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_normal));
                controllerLandscapeBtnStream360P.setBackground(getResources().getDrawable(R.drawable.background_btn_stream_pressed));
                controllerLandscapeBtnStream1080P.setTextColor(getResources().getColor(R.color.color_icons));
                controllerLandscapeBtnStream360P.setTextColor(getResources().getColor(R.color.color_primary));

                for (LiveDetailBean.StreamListBean stream :
                        streamList) {
                    if (stream.getType().equals(getString(R.string.stream_360p))) {
                        live_url = stream.getUrl();
                    }
                }
                mediaPlayer.reset();
                //显示ProgressBar
                isVideoPrepared = false;
                livePlayProgreeBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
                try {
                    mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.prepareAsync();
                break;

            //播放&暂停
            case R.id.controller_landscape_iv_play_pause:
                if (isPause == true) {
                    onResume();
                } else if (isPause == false) {
                    onPause();
                }
                controllerLandscapeIvPlayPause.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            //重新加载
            case R.id.controller_landscape_iv_refresh:
                try {
                    mediaPlayer.reset();

                    isVideoPrepared = false;
                    livePlayProgreeBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);

                    mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            //横屏发送弹幕
            case R.id.controller_landscape_btn_senddanmu:
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

                String danmu = controllerLandscapeEtDanmu.getText().toString();
                if (TextUtils.isEmpty(danmu)) {
                    SnackbarUtil.show("发送弹幕内容不能为空");
                    return;
                }

                //新建弹幕对象
                DanmuBean danmuBean = new DanmuBean();
                DanmuBean.DataBean dataBean = new DanmuBean.DataBean();
                DanmuBean.DataBean.FromBean fromBean = new DanmuBean.DataBean.FromBean();
                fromBean.setNickName(getString(R.string.chat_name));
                fromBean.setUserName(getString(R.string.chat_name));
                dataBean.setFrom(fromBean);
                dataBean.setContent(danmu);
                danmuBean.setData(dataBean);

                this.addDanmuOnDanmakuView(danmuBean, true); //添加弹幕至DanmakuView
                chatFragment.addDanmuOnRecyclerView(danmuBean);//添加弹幕至RecyclerView

                controllerLandscapeEtDanmu.setText(null);
                break;

            //横屏弹幕显示&隐藏
            case R.id.controller_landscape_iv_danmu_visible:

                if (isShowDanmu == true) {//已开启弹幕
                    isShowDanmu = false;
                    controllerLandscapeIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    controllerPortraitIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    SnackbarUtil.show("弹幕已关闭！");
                } else if (isShowDanmu == false) {//已关闭弹幕
                    isShowDanmu = true;
                    controllerLandscapeIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    controllerPortraitIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    SnackbarUtil.show("弹幕已开启！");
                }

                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            //退出全屏
            case R.id.controller_landscape_iv_fullscreen_exit:
                exitFullscreen();
                break;

            //竖屏Back
            case R.id.controller_portrait_iv_back:
                onBackPressedSupport();
                break;

            //竖屏弹幕显示隐藏
            case R.id.controller_portrait_iv_danmu_visible:
                if (isShowDanmu == true) {//已开启弹幕
                    isShowDanmu = false;
                    controllerLandscapeIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    controllerPortraitIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    SnackbarUtil.show("弹幕已关闭！");
                } else if (isShowDanmu == false) {//已关闭弹幕
                    isShowDanmu = true;
                    controllerLandscapeIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    controllerPortraitIvDanmuVisible.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    SnackbarUtil.show("弹幕已开启！");
                }

                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            case R.id.controller_portrait_iv_fullscreen://进入全屏
                enterFullscreen();
                break;
        }
    }

    /**
     * ListPlayChatFragment调用，添加弹幕到DanmakuView
     * @param danmuBean
     * @param withBorder
     */
    public void addDanmuOnDanmakuView(DanmuBean danmuBean, boolean withBorder) {
        if (isShowDanmu == false) {
            return;
        }
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = danmuBean.getData().getContent();
        danmaku.textSize = SizeUtils.sp2px(context, 12 * 1.0f);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = getResources().getColor(R.color.color_primary);
        }
        danmuView.addDanmaku(danmaku);
    }

    //进入全屏
    private void enterFullscreen() {

        livePlayTopLayout.removeView(surfaceView);
        livePlayTopLayout.removeView(livePlayProgreeBar);
        livePlayTopLayout.removeView(danmuView);
        livePlayTopLayout.removeView(controllerLandscapeLayout);
        livePlayTopLayout.removeView(controllerPortraitLayout);

        ScreenUtils.setLandscape(this);

        isFullscreen = true;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        livePlayTopLayout.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(livePlayProgreeBar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(danmuView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerLandscapeLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerPortraitLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //全屏隐藏状态栏
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);

        livePlayBottomLayout.setVisibility(View.GONE);
//        livePlayProgreeBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
        controllerLandscapeLayout.setVisibility(View.VISIBLE);
        controllerPortraitLayout.setVisibility(View.GONE);
        controllerLandscapeIvPlayPause.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);

    }

    //退出全屏
    private void exitFullscreen() {

        livePlayTopLayout.removeView(surfaceView);
        livePlayTopLayout.removeView(livePlayProgreeBar);
        livePlayTopLayout.removeView(danmuView);
        livePlayTopLayout.removeView(controllerLandscapeLayout);
        livePlayTopLayout.removeView(controllerPortraitLayout);

        ScreenUtils.setPortrait(this);

        isFullscreen = false;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        livePlayTopLayout.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(livePlayProgreeBar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(danmuView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerLandscapeLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerPortraitLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        onVideoSizeChanged(mediaPlayer, videoWidth, videoHeight);

        //显示状态栏
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(lp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        livePlayBottomLayout.setVisibility(View.VISIBLE);
//        livePlayProgreeBar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
        controllerLandscapeLayout.setVisibility(View.GONE);
        controllerPortraitLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == HANDLER_HIDE_CONTROLLER) {
            //hide controller
            controllerLandscapeLayout.setVisibility(View.GONE);
            controllerPortraitLayout.setVisibility(View.GONE);
            isControllerHiden = true;
        }
        return true;
    }

}
