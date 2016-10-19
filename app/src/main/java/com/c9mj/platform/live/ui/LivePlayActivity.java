package com.c9mj.platform.live.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;
import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LivePandaBean;
import com.c9mj.platform.live.mvp.presenter.impl.LivePlayPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILivePlayActivity;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.util.retrofit.exception.MediaException;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * author: LMJ
 * date: 2016/9/12
 * 观看直播Activity
 */
public class LivePlayActivity extends SwipeBackActivity
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

    private Context context;
    private LivePlayPresenterImpl presenter;
    private Handler controllerHandler;

    @BindView(R.id.surfaceview)
    SurfaceView surfaceView;                  //用于显示播放画面
    private PLMediaPlayer mediaPlayer;  //媒体控制器
    private AVOptions avOptions;        //播放参数配置

    @BindView(R.id.controller_landscape_iv_back)
    ImageView controllerLandscapeIvBack;
    @BindView(R.id.controller_landscape_tv_roomname)
    TextView controllerLandscapeTvRoomname;
    @BindView(R.id.controller_landscape_btn_stream)
    Button controllerLandscapeBtnStream;
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
    @BindView(R.id.controller_portrait_iv_back)
    ImageView controllerPortraitIvBack;
    @BindView(R.id.controller_portrait_iv_fullscreen)
    ImageView controllerPortraitIvFullscreen;
    @BindView(R.id.controller_portrait_layout)
    RelativeLayout controllerPortraitLayout;
    @BindView(R.id.live_play_progressbar)
    FrameLayout livePlayProgreeBar;
    @BindView(R.id.live_play_top_layout)
    FrameLayout livePlayTopLayout;
    @BindView(R.id.live_play_bottom_layout)
    FrameLayout livePlayBottomLayout;

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
//        initController();

        presenter.getLiveDetail(live_type, live_id, game_type);     //请求直播详情
        presenter.getDanmuDetail(live_id, live_type);                          //请求弹幕服务器相关参数
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPause = true;
//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.abandonAudioFocus(null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
//            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            audioManager.abandonAudioFocus(null);
        }

    }

    @Override
    public void onBackPressedSupport() {
        if (isFullscreen == true){
            exitFullscreen();
        }else {
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
        enterFullscreen();
        exitFullscreen();
        livePlayProgreeBar.setVisibility(View.VISIBLE);
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
            List<LiveDetailBean.StreamListBean> streamList = detailBean.getStream_list();
            live_url = streamList.get(streamList.size() - 1).getUrl();
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

    }

    @Override
    public void updateChatDetail(LivePandaBean pandaBean) {
        presenter.connectToChatRoom(live_id, pandaBean);
    }

    @Override
    public void showError(String message) {
        ToastUtil.show(message);
    }


    /********
     * 以下实现的Interface都是MediaPlayer的监听
     *********/
    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer) {
        isVideoPrepared = true;
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
                livePlayProgreeBar.setVisibility(View.VISIBLE);
                break;
            case PLMediaPlayer.MEDIA_INFO_BUFFERING_END:
            case PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START://视频缓冲完成可播放
                livePlayProgreeBar.setVisibility(View.GONE);
                isPause = false;
                controllerLandscapeIvPlayPause.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
        showError(new MediaException(errorCode).getMessage());
        return true;
    }

    @OnClick({R.id.surfaceview, R.id.controller_landscape_iv_back, R.id.controller_landscape_btn_stream, R.id.controller_landscape_iv_play_pause, R.id.controller_landscape_iv_refresh, R.id.controller_landscape_btn_senddanmu, R.id.controller_landscape_iv_danmu_visible, R.id.controller_landscape_iv_fullscreen_exit, R.id.controller_portrait_iv_back, R.id.controller_portrait_iv_fullscreen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.surfaceview:
                if (isFullscreen == true){
                    if (isControllerHiden == true) {//全屏&&隐藏
                        controllerLandscapeLayout.setVisibility(View.VISIBLE);
                        controllerPortraitLayout.setVisibility(View.GONE);
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                        isControllerHiden = false;
                    }else if (isControllerHiden == false){//全屏&&显示
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessage(HANDLER_HIDE_CONTROLLER);
                    }
                }else if (isFullscreen == false){
                    if (isControllerHiden == true) {//非全屏&&隐藏
                        controllerLandscapeLayout.setVisibility(View.GONE);
                        controllerPortraitLayout.setVisibility(View.VISIBLE);
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                        isControllerHiden = false;
                    }else if (isControllerHiden == false){//非全屏&&显示
                        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                        controllerHandler.sendEmptyMessage(HANDLER_HIDE_CONTROLLER);
                    }
                }
                break;
            case R.id.controller_landscape_iv_back://全屏Back
                onBackPressedSupport();
                break;
            case R.id.controller_landscape_btn_stream://直播流连接切换
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;
            case R.id.controller_landscape_iv_play_pause://播放/暂停
                if (isPause == true){
                    onResume();
                }else if (isPause == false){
                    onPause();
                }
                controllerLandscapeIvPlayPause.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;
            case R.id.controller_landscape_iv_refresh://重新加载
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(live_url);//加载直播链接进行播放
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;
            case R.id.controller_landscape_btn_senddanmu://发送弹幕
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;
            case R.id.controller_landscape_iv_danmu_visible://弹幕显示&隐藏
                controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
                controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);
                break;
            case R.id.controller_landscape_iv_fullscreen_exit://退出全屏
                exitFullscreen();
                break;
            case R.id.controller_portrait_iv_back://竖屏Back
                onBackPressedSupport();
                break;
            case R.id.controller_portrait_iv_fullscreen://进入全屏
                enterFullscreen();
                break;
        }
    }

    //进入全屏
    private void enterFullscreen() {

        livePlayTopLayout.removeView(surfaceView);
        livePlayTopLayout.removeView(livePlayProgreeBar);
        livePlayTopLayout.removeView(controllerLandscapeLayout);
        livePlayTopLayout.removeView(controllerPortraitLayout);

        livePlayBottomLayout.setVisibility(View.GONE);
        livePlayProgreeBar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
        controllerLandscapeLayout.setVisibility(View.VISIBLE);
        controllerPortraitLayout.setVisibility(View.GONE);
        ScreenUtils.setLandscape(this);

        isFullscreen = true;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        livePlayTopLayout.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(livePlayProgreeBar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerLandscapeLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerPortraitLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //全屏隐藏状态栏
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);

        controllerLandscapeIvPlayPause.setImageResource(isPause ? R.drawable.selector_btn_play : R.drawable.selector_btn_pause);

    }

    //退出全屏
    private void exitFullscreen() {

        livePlayTopLayout.removeView(surfaceView);
        livePlayTopLayout.removeView(livePlayProgreeBar);
        livePlayTopLayout.removeView(controllerLandscapeLayout);
        livePlayTopLayout.removeView(controllerPortraitLayout);

        livePlayBottomLayout.setVisibility(View.VISIBLE);
        livePlayProgreeBar.setVisibility(isVideoPrepared ? View.GONE : View.VISIBLE);
        controllerLandscapeLayout.setVisibility(View.GONE);
        controllerPortraitLayout.setVisibility(View.VISIBLE);
        ScreenUtils.setPortrait(this);

        isFullscreen = false;
        isControllerHiden = false;
        controllerHandler.removeMessages(HANDLER_HIDE_CONTROLLER);
        controllerHandler.sendEmptyMessageDelayed(HANDLER_HIDE_CONTROLLER, HANDLER_CONTROLLER_DURATION);

        livePlayTopLayout.addView(surfaceView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(livePlayProgreeBar, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerLandscapeLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        livePlayTopLayout.addView(controllerPortraitLayout, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        onVideoSizeChanged(mediaPlayer, videoWidth, videoHeight);

        //显示状态栏
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(lp);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == HANDLER_HIDE_CONTROLLER){
            //hide controller
            controllerLandscapeLayout.setVisibility(View.GONE);
            controllerPortraitLayout.setVisibility(View.GONE);
            isControllerHiden = true;
        }
        return true;
    }

}
