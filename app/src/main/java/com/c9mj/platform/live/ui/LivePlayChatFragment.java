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
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.c9mj.platform.R;
import com.c9mj.platform.live.adapter.LivePlayChatAdapter;
import com.c9mj.platform.live.mvp.model.DanmuBean;
import com.c9mj.platform.widget.fragment.BaseFragment;
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
public class LivePlayChatFragment extends BaseFragment {

    private static final String KEY = "key";

    private static final int DANMU_LIMIT = 30;
    private final List<DanmuBean> danmuList = new ArrayList<>();
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.et_danmu)
    EditText et_danmu;
    @BindView(R.id.tv_send)
    TextView tv_send;
    private LinearLayoutManager layoutManager;
    private LivePlayChatAdapter adapter;
    private Context context;
    private LivePlayActivity activity;

    public static LivePlayChatFragment newInstance() {
        return newInstance("");
    }

    private static LivePlayChatFragment newInstance(String game_type) {
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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initView();
    }

    private void initView() {
        //初始化MVP

        //设置RefreshLayout

        //设置RecyclerView
        adapter = new LivePlayChatAdapter(danmuList);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        /***设置其他View***/

    }

    @OnClick(R.id.tv_send)
    public void onClick() {
        String danmu = et_danmu.getText().toString();
        if (TextUtils.isEmpty(danmu)) {
            ToastUtils.showShortToast("发送弹幕内容不能为空");
            return;
        } else if (activity == null) {
            ToastUtils.showShortToast("发送弹幕内容失败");
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

        et_danmu.setText(null);
    }

    /**
     * ListPlayActivity调用，添加弹幕到RecyclerView
     *
     * @param danmuBean
     */
    public void addDanmuOnRecyclerView(DanmuBean danmuBean) {

        if (adapter == null) {
            return;
        }
        if (adapter.getData().size() >= DANMU_LIMIT) {
            adapter.remove(0);
        }
        adapter.add(adapter.getData().size(), danmuBean);
        recyclerView.scrollToPosition(adapter.getData().size() - 1);

    }


}
