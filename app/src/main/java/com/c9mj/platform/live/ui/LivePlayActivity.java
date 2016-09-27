package com.c9mj.platform.live.ui;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.c9mj.platform.R;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailDouyuBean;
import com.c9mj.platform.live.mvp.presenter.impl.LivePlayPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILivePlayChatFragment;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.util.retrofit.exception.MediaException;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * author: LMJ
 * date: 2016/9/12
 * 观看直播Activity
 */
public class LivePlayActivity extends SwipeBackActivity
        implements ILivePlayChatFragment,
        PLMediaPlayer.OnPreparedListener,
        PLMediaPlayer.OnVideoSizeChangedListener,
        PLMediaPlayer.OnCompletionListener,
        PLMediaPlayer.OnInfoListener,
        PLMediaPlayer.OnErrorListener{

    public static final String LIVE_TYPE = "live_type"; //直播平台
    public static final String LIVE_ID = "live_id";     //直播房间ID
    public static final String GAME_TYPE = "game_type"; //直播游戏类型
    public static final String DOUYU_URL = "douyu_url"; //斗鱼直播url

    private String live_type;   //直播平台
    private String live_id;     //直播房间号ID
    private String game_type;   //直播游戏类型
    private String douyu_url;   //斗鱼直播专属url

    private int surfaceWidth;
    private int surfaceHeight;
    private boolean isStop = false;

    private Context context;
    private LivePlayPresenterImpl presenter;

    @BindView(R.id.surfaceview)
    SurfaceView sv_live;                  //显示画面
    private PLMediaPlayer mediaPlayer;  //媒体控制器
    private AVOptions avOptions;        //播放参数配置


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
        initSurfaceView();

        presenter.getLiveDetail(live_type, live_id, game_type);     //请求直播详情
        presenter.getDanmuDetail(douyu_url);                          //请求弹幕服务器相关参数
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && isStop == true) {
            mediaPlayer.prepareAsync();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isStop = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(null);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }
    private void initMVP() {
        presenter = new LivePlayPresenterImpl(context, this);
    }

    /**
     * 监听SurfaceView回调
     */
    private void initSurfaceView() {
        sv_live.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                prepareMediaPlayer();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                surfaceWidth = width;
                surfaceHeight = height;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mediaPlayer != null){
                    mediaPlayer.setDisplay(null);
                }
            }
        });
    }

    /**
     * 配置MediaPlayer相关参数
     */
    private void prepareMediaPlayer() {

        if (mediaPlayer != null) {
            mediaPlayer.setDisplay(sv_live.getHolder());
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
            avOptions.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION,10 * 1000);
            avOptions.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 15 * 1000);

            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            mediaPlayer = new PLMediaPlayer(avOptions);

            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnErrorListener(this);

            mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
//            mediaPlayer.setDataSource(live_url);
            mediaPlayer.setDisplay(sv_live.getHolder());
            mediaPlayer.prepareAsync();
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void updateLiveDetail(LiveDetailBean detailBean) {
        String live_url;
        try {
            live_url = detailBean.getStream_list().get(1).getUrl();
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
    public void updateDouyuDetail(LiveDetailDouyuBean detailDouyuBean) {

    }

    @Override
    public void showError(String message) {
        ToastUtil.show(message);
    }


    /********以下实现的Interface都是MediaPlayer的监听*********/
    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height) {
        if (width != 0 && height != 0) {
            float ratioW = (float) width/(float) surfaceWidth;
            float ratioH = (float) height/(float) surfaceHeight;
            float ratio = Math.max(ratioW, ratioH);
            width  = (int) Math.ceil((float)width/ratio);
            height = (int) Math.ceil((float)height/ratio);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
            lp.gravity = Gravity.CENTER;
            sv_live.setLayoutParams(lp);
        }
    }

    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {

    }

    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
        switch (what) {
            case PLMediaPlayer.MEDIA_INFO_BUFFERING_START:
//                mLoadingView.setVisibility(View.VISIBLE);
                break;
            case PLMediaPlayer.MEDIA_INFO_BUFFERING_END:
            case PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                mLoadingView.setVisibility(View.GONE);
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
}
