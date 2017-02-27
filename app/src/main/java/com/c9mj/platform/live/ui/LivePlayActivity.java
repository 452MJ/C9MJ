package com.c9mj.platform.live.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
import com.blankj.utilcode.utils.ToastUtils;
import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.model.DanmuBean;
import com.c9mj.platform.live.mvp.model.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.LivePandaBean;
import com.c9mj.platform.live.mvp.presenter.impl.LivePlayPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILivePlayActivity;
import com.c9mj.platform.util.retrofit.exception.MediaException;
import com.c9mj.platform.widget.activity.BaseSwipeActivity;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
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
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author: LMJ
 * date: 2016/9/12
 * 观看直播Activity
 */
public class LivePlayActivity extends BaseSwipeActivity
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

    private static final int HANDLER_HIDE_CONTROLLER = 100;//隐藏MediaController
    private static final int HANDLER_CONTROLLER_DURATION = 5 * 1000;//MediaController显示时间
    private final String[] indicatorText = new String[]{
            "聊天",
            "主播"
    };
    private final int[] normalResId = new int[]{
            R.drawable.ic_danmu_on_normal_dark,
            R.drawable.ic_avatar_normal
    };
    private final int[] pressedResId = new int[]{
            R.drawable.ic_danmu_on_pressed,
            R.drawable.ic_avatar_pressed
    };
    private final BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    private final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper();
    private final List<String> titleList = new ArrayList<>();
    private final SupportFragment[] fragments = new SupportFragment[2];
    @BindView(R.id.surfaceview)
    SurfaceView surfaceView;                  //用于显示播放画面
    @BindView(R.id.danmuview)
    DanmakuView danmuView;
    //横屏控件
    @BindView(R.id.iv_back_landscape)
    ImageView iv_back_landscape;
    @BindView(R.id.tv_roomname_landscape)
    TextView tv_roomname_landscape;
    @BindView(R.id.btn_stream_1080p_landscape)
    Button btn_stream_1080p_landscape;
    @BindView(R.id.btn_stream_360p_landscape)
    Button btn_stream_360p_landscape;
    @BindView(R.id.iv_play_pause_landscape)
    ImageView iv_play_pause_landscape;
    @BindView(R.id.iv_refresh_landscape)
    ImageView iv_refresh_landscape;
    @BindView(R.id.et_danmu_landscape)
    EditText et_danmu_landscape;
    @BindView(R.id.btn_send_landscape)
    Button btn_send_landscape;
    @BindView(R.id.iv_danmu_visible_landscape)
    ImageView iv_danmu_visible_landscape;
    @BindView(R.id.iv_fullscreen_exit_landscape)
    ImageView iv_fullscreen_exit_landscape;
    @BindView(R.id.layout_landscape)
    RelativeLayout layout_landscape;
    //竖屏控件
    @BindView(R.id.iv_back_portrait)
    ImageView iv_back_portrait;
    @BindView(R.id.iv_danmu_visible_portrait)
    ImageView iv_danmu_visible_portrait;
    @BindView(R.id.iv_fullscreen_portrait)
    ImageView iv_fullscreen_portrait;
    @BindView(R.id.layout_portrait)
    RelativeLayout layout_portrait;
    @BindView(R.id.progressbar)
    FrameLayout progressbar;
    @BindView(R.id.layout_top)
    FrameLayout layout_top;
    //底部Layout相关
    @BindView(R.id.layout_bottom)
    LinearLayout layout_bottom;
    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
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
    private boolean isShowDanmu = false;// 弹幕显示标志位
    private DanmakuContext danmakuContext;
    private CommonNavigatorAdapter navigatorAdapter;
    private LivePlayChatFragment chatFragment;//弹幕聊天室Fragment
    private LivePlayAvatarFragment avatarFragment;//主播详情Fragment
    private int current;
    private PLMediaPlayer mediaPlayer;  //媒体控制器

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

        initView();

        presenter.getLiveDetail(live_type, live_id, game_type);     //请求直播详情
        presenter.getDanmuDetail(live_id, live_type);                          //请求弹幕服务器相关参数
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (mediaPlayer != null && isPause && !TextUtils.isEmpty(live_url)) {
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
        progressbar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onBackPressedSupport() {
        if (isFullscreen) {
            exitFullscreen();
        } else {
            super.onBackPressedSupport();
        }
    }

    private void initView() {
        //初始化MVP
        presenter = new LivePlayPresenterImpl(this);

        //设置RefreshLayout

        //设置RecyclerView

        /***设置其他View***/
        tv_roomname_landscape.setSelected(true);

        //SurfaceView监听回调
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                progressbar.setVisibility(View.VISIBLE);
                prepareMediaPlayer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (!isSurfaceViewInit) {
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

        //MediaController
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        et_danmu_landscape.setOnTouchListener((v, event) -> {
            //点击弹幕编辑时取消隐藏Controller
            controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
            return false;
        });

        //弹幕烈焰使
        iv_danmu_visible_landscape.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
        iv_danmu_visible_portrait.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);

        danmuView.enableDanmakuDrawingCache(true);//打开绘图缓存，提升绘制效率
        danmuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                try {
                    isShowDanmu = true;
                    danmuView.start();
                    iv_danmu_visible_landscape.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    iv_danmu_visible_portrait.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
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

        //ViewPager + Indicator
        titleList.add(indicatorText[0]);
        titleList.add(indicatorText[1]);

        chatFragment = LivePlayChatFragment.newInstance();
        avatarFragment = LivePlayAvatarFragment.newInstance();

        fragments[0] = chatFragment;
        fragments[1] = avatarFragment;

        loadMultipleRootFragment(R.id.layout_container, 0, fragments);
        current = 0;

        CommonNavigator navigator = new CommonNavigator(context);
        navigator.setAdjustMode(true);
        navigator.setFollowTouch(true);
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public IPagerTitleView getTitleView(final Context context, final int index) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context);

                titleView.setContentView(R.layout.item_live_play_indicator_layout);//加载自定义布局作为Tab

                final ImageView live_play_iv_icon = (ImageView) titleView.findViewById(R.id.live_play_iv_icon);
                final TextView live_play_tv_title = (TextView) titleView.findViewById(R.id.live_play_tv_title);
                live_play_tv_title.setText(indicatorText[index]);
                titleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int index, int totalCount) {
                        live_play_iv_icon.setImageResource(pressedResId[index]);
                        live_play_tv_title.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
                    }

                    @Override
                    public void onDeselected(int index, int totalCount) {
                        live_play_iv_icon.setImageResource(normalResId[index]);
                        live_play_tv_title.setTextColor(ContextCompat.getColor(context, R.color.color_secondary_text));
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });

                titleView.setOnClickListener(v -> {
                    fragmentContainerHelper.handlePageSelected(index);
                    showHideFragment(fragments[index], fragments[current]);
                    current = index;
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
                indicator.setColors(ContextCompat.getColor(context, R.color.color_primary));
                return indicator;
            }
        };

        navigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(navigator);
        fragmentContainerHelper.attachMagicIndicator(indicator);
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
            AVOptions avOptions = new AVOptions();
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateLiveDetail(LiveDetailBean detailBean) {
        try {
            tv_roomname_landscape.setText(detailBean.getLive_title());
            streamList = detailBean.getStream_list();
            LiveDetailBean.StreamListBean stream = streamList.get(streamList.size() - 1);
            live_url = stream.getUrl();
            if (streamList.size() == 1) {
                if (stream.getType().equals(getString(R.string.stream_1080p))) {
                    btn_stream_360p_landscape.setVisibility(View.GONE);
                }
                if (stream.getType().equals(getString(R.string.stream_360p))) {
                    btn_stream_1080p_landscape.setVisibility(View.GONE);
                }
            }
            if (stream.getType().equals(getString(R.string.stream_1080p))) {
                btn_stream_1080p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_pressed));
                btn_stream_360p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_normal));
                btn_stream_1080p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
                btn_stream_360p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_icons));
            }
            if (stream.getType().equals(getString(R.string.stream_360p))) {
                btn_stream_1080p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_normal));
                btn_stream_360p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_pressed));
                btn_stream_1080p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_icons));
                btn_stream_360p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
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
        if (chatFragment != null) {
            chatFragment.addDanmuOnRecyclerView(danmuBean);
        }
    }

    @Override
    public void showError(String message) {
        ToastUtils.showShortToast(message);
    }


    /********
     * 以下实现的Interface都是MediaPlayer的监听
     *********/
    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer) {
        progressbar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
        mediaPlayer.start();
    }

    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int i2, int i3) {
        videoWidth = width;
        videoHeight = height;

        if (videoWidth != 0 && videoHeight != 0) {
            float ratioW = (float) videoWidth / (float) (isFullscreen ? ScreenUtils.getScreenWidth() : surfacePortraitWidth);
            float ratioH = (float) videoHeight / (float) (isFullscreen ? ScreenUtils.getScreenHeight() : surfacePortraitHeight);
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
                progressbar.setVisibility(View.GONE);
                isVideoPrepared = true;
                isPause = false;
                iv_play_pause_landscape.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
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
            R.id.iv_back_landscape,
            R.id.btn_stream_1080p_landscape,
            R.id.btn_stream_360p_landscape,
            R.id.iv_play_pause_landscape,
            R.id.iv_refresh_landscape,
            R.id.btn_send_landscape,
            R.id.iv_danmu_visible_landscape,
            R.id.iv_fullscreen_exit_landscape,
            R.id.iv_back_portrait,
            R.id.iv_danmu_visible_portrait,
            R.id.iv_fullscreen_portrait
    })
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.surfaceview:
                if (isFullscreen) {
                    if (isControllerHiden) {//全屏&&隐藏
                        layout_landscape.setVisibility(View.VISIBLE);
                        layout_portrait.setVisibility(View.GONE);
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                        isControllerHiden = false;
                    } else if (!isControllerHiden) {//全屏&&显示
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessage(HANDLER_HIDE_CONTROLLER);
                    }
                } else if (!isFullscreen) {
                    if (isControllerHiden) {//非全屏&&隐藏
                        layout_landscape.setVisibility(View.GONE);
                        layout_portrait.setVisibility(View.VISIBLE);
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                        isControllerHiden = false;
                    } else if (!isControllerHiden) {//非全屏&&显示
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessage(HANDLER_HIDE_CONTROLLER);
                    }
                }
                break;

            //全屏Back
            case R.id.iv_back_landscape:
                onBackPressedSupport();
                break;

            //直播流连接切换（超清）
            case R.id.btn_stream_1080p_landscape:
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                btn_stream_1080p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_pressed));
                btn_stream_360p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_normal));
                btn_stream_1080p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
                btn_stream_360p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_icons));

                for (LiveDetailBean.StreamListBean stream :
                        streamList) {
                    if (stream.getType().equals(getString(R.string.stream_1080p))) {
                        live_url = stream.getUrl();
                    }
                }
                mediaPlayer.reset();
                //显示ProgressBar
                isVideoPrepared = false;
                progressbar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
                try {
                    mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.prepareAsync();
                break;

            //直播流连接切换（普清）
            case R.id.btn_stream_360p_landscape:
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                btn_stream_1080p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_normal));
                btn_stream_360p_landscape.setBackground(ContextCompat.getDrawable(context, R.drawable.background_btn_stream_pressed));
                btn_stream_1080p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_icons));
                btn_stream_360p_landscape.setTextColor(ContextCompat.getColor(context, R.color.color_primary));

                for (LiveDetailBean.StreamListBean stream :
                        streamList) {
                    if (stream.getType().equals(getString(R.string.stream_360p))) {
                        live_url = stream.getUrl();
                    }
                }
                mediaPlayer.reset();
                //显示ProgressBar
                isVideoPrepared = false;
                progressbar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
                try {
                    mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.prepareAsync();
                break;

            //播放&暂停
            case R.id.iv_play_pause_landscape:
                if (isPause) {
                    onResume();
                } else if (!isPause) {
                    onPause();
                }
                iv_play_pause_landscape.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            //重新加载
            case R.id.iv_refresh_landscape:
                try {
                    mediaPlayer.reset();

                    isVideoPrepared = false;
                    progressbar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);

                    mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            //横屏发送弹幕
            case R.id.btn_send_landscape:
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

                String danmu = et_danmu_landscape.getText().toString();
                if (TextUtils.isEmpty(danmu)) {
                    ToastUtils.showShortToast("发送弹幕内容不能为空");
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

                et_danmu_landscape.setText(null);
                break;

            //横屏弹幕显示&隐藏
            case R.id.iv_danmu_visible_landscape:

                if (isShowDanmu) {//已开启弹幕
                    isShowDanmu = false;
                    iv_danmu_visible_landscape.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    iv_danmu_visible_portrait.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    ToastUtils.showShortToast("弹幕已关闭！");
                } else if (!isShowDanmu) {//已关闭弹幕
                    isShowDanmu = true;
                    iv_danmu_visible_landscape.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    iv_danmu_visible_portrait.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    ToastUtils.showShortToast("弹幕已开启！");
                }

                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            //退出全屏
            case R.id.iv_fullscreen_exit_landscape:
                exitFullscreen();
                break;

            //竖屏Back
            case R.id.iv_back_portrait:
                onBackPressedSupport();
                break;

            //竖屏弹幕显示隐藏
            case R.id.iv_danmu_visible_portrait:
                if (isShowDanmu) {//已开启弹幕
                    isShowDanmu = false;
                    iv_danmu_visible_landscape.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    iv_danmu_visible_portrait.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    ToastUtils.showShortToast("弹幕已关闭！");
                } else if (!isShowDanmu) {//已关闭弹幕
                    isShowDanmu = true;
                    iv_danmu_visible_landscape.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    iv_danmu_visible_portrait.setImageResource(isShowDanmu ? R.drawable.selector_btn_danmu_on : R.drawable.selector_btn_danmu_off);
                    ToastUtils.showShortToast("弹幕已开启！");
                }

                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;

            case R.id.iv_fullscreen_portrait://进入全屏
                enterFullscreen();
                break;
        }
    }

    /**
     * ListPlayChatFragment调用，添加弹幕到DanmakuView
     *
     * @param danmuBean
     * @param withBorder
     */
    public void addDanmuOnDanmakuView(DanmuBean danmuBean, boolean withBorder) {
        if (!isShowDanmu) {
            return;
        }
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = danmuBean.getData().getContent();
        danmaku.textSize = SizeUtils.sp2px(12 * 1.0f);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = ContextCompat.getColor(context, R.color.color_primary);
        }
        danmuView.addDanmaku(danmaku);
    }

    //进入全屏
    private void enterFullscreen() {

        layout_top.removeView(surfaceView);
        layout_top.removeView(progressbar);
        layout_top.removeView(danmuView);
        layout_top.removeView(layout_landscape);
        layout_top.removeView(layout_portrait);

        ScreenUtils.setLandscape(this);

        isFullscreen = true;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        layout_top.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(progressbar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(danmuView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(layout_landscape, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(layout_portrait, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //全屏隐藏状态栏
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);

        layout_bottom.setVisibility(View.GONE);
//        progressbar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
        layout_landscape.setVisibility(View.VISIBLE);
        layout_portrait.setVisibility(View.GONE);
        iv_play_pause_landscape.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);

    }

    //退出全屏
    private void exitFullscreen() {

        layout_top.removeView(surfaceView);
        layout_top.removeView(progressbar);
        layout_top.removeView(danmuView);
        layout_top.removeView(layout_landscape);
        layout_top.removeView(layout_portrait);

        ScreenUtils.setPortrait(this);

        isFullscreen = false;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        layout_top.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(progressbar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(danmuView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(layout_landscape, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout_top.addView(layout_portrait, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        onVideoSizeChanged(mediaPlayer, videoWidth, videoHeight, 0, 0);

        //显示状态栏
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(lp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout_bottom.setVisibility(View.VISIBLE);
//        progressbar.setVisibility(isVideoPrepared == true ? View.GONE : View.VISIBLE);
        layout_landscape.setVisibility(View.GONE);
        layout_portrait.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == HANDLER_HIDE_CONTROLLER) {
            //hide controller
            layout_landscape.setVisibility(View.GONE);
            layout_portrait.setVisibility(View.GONE);
            isControllerHiden = true;
        }
        return true;
    }

}
