package com.c9mj.platform.live.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.c9mj.platform.R;
import com.c9mj.platform.live.adapter.LivePlayChatAdapter;
import com.c9mj.platform.live.mvp.model.bean.DanmuBean;
import com.c9mj.platform.util.SnackbarUtil;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.widget.fragment.LazyFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: LMJ
 * date: 2016/10/27
 * 直播播放页面（聊天室）
 */
public class LivePlayChatFragment extends LazyFragment {

    private static final String KEY = "key";

    private static final int DANMU_LIMIT = 30;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    LivePlayChatAdapter adapter;
    List<DanmuBean> danmuList = new ArrayList<>();

    @BindView(R.id.live_play_chat_fragment_et_danmu)
    EditText livePlayChatFragmentEtDanmu;
    @BindView(R.id.live_play_chat_fragment_btn_send)
    Button livePlayChatFragmentBtnSend;

    private Context context;
    private LivePlayActivity activity;

    public static LivePlayChatFragment newInstance() {
        return newInstance("");
    }

    public static LivePlayChatFragment newInstance(String game_type) {
        LivePlayChatFragment fragment = new LivePlayChatFragment();
        Bundle args = new Bundle();
        args.putString(KEY, game_type);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (LivePlayActivity) activity;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            initRecyclerView();
        }
    }

    private void initRecyclerView() {
        adapter = new LivePlayChatAdapter(danmuList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
    @OnClick(R.id.live_play_chat_fragment_btn_send)
    public void onClick() {
        String danmu = livePlayChatFragmentEtDanmu.getText().toString();
        if (TextUtils.isEmpty(danmu)) {
            SnackbarUtil.show("发送弹幕内容不能为空");
            return;
        }else if (activity == null){
            SnackbarUtil.show("发送弹幕内容失败");
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

        activity.addDanmuOnDanmakuView(danmuBean, true);
        this.addDanmuOnRecyclerView(danmuBean);

        livePlayChatFragmentEtDanmu.setText(null);
    }

    /**
     * ListPlayActivity调用，添加弹幕到RecyclerView
     * @param danmuBean
     */
    public void addDanmuOnRecyclerView(DanmuBean danmuBean) {

        if (adapter == null){
            return;
        }
        if (adapter.getData().size() >= DANMU_LIMIT){
            adapter.remove(0);
        }
        adapter.add(adapter.getData().size(), danmuBean);
        recyclerView.scrollToPosition(adapter.getData().size() - 1);

    }


}
