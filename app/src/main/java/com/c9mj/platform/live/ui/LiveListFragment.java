package com.c9mj.platform.live.ui;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.live.adapter.LiveListAdapter;
import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.bean.LiveListItemBean;
import com.c9mj.platform.live.mvp.presenter.impl.LiveListPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILiveListFragment;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.widget.animation.CustionAnimation;
import com.c9mj.platform.widget.fragment.LazyFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: LMJ
 * date: 2016/9/19
 * 直播列表
 */
public class LiveListFragment extends LazyFragment implements ILiveListFragment,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private static final String GAME_TYPE = "game_type";

    private String game_type;
    private int offset = 0;//用于记录分页偏移量
    private List<LiveListItemBean> liveList = new ArrayList<>();
    private Set<String> platformList = new HashSet<>();//douyu, huomaotv, yy, panda, longzhu, cc, quanmin, zhanqi

    private Context context;
    private LiveListPresenterImpl presenter;

    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    LiveListAdapter adapter;

    public static LiveListFragment newInstance() {
        return newInstance("");
    }

    public static LiveListFragment newInstance(String game_type) {
        LiveListFragment fragment = new LiveListFragment();
        Bundle args = new Bundle();
        args.putString(GAME_TYPE, game_type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_list, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();
        game_type = getArguments().getString(GAME_TYPE);//得到传入的cate_id

        initMVP();
        initRefreshView();
        initRecyclerView();

        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            refreshLayout.setProgressViewOffset(false, 0, 30);// 这句话是为了，第一次进入页面初始化数据的时候显示加载进度条
            refreshLayout.setRefreshing(true);

            //根据game_type分类请求直播数据
            presenter.getLiveList(offset, LiveAPI.LIMIT, game_type );
        }
    }

    private void initMVP() {
        presenter = new LiveListPresenterImpl(context, this);
    }

    private void initRefreshView() {
        refreshLayout.setColorSchemeResources(R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new LiveListAdapter(liveList);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        adapter.openLoadAnimation(new CustionAnimation());
        adapter.isFirstOnly(true);
        adapter.openLoadMore(LiveAPI.LIMIT);//加载更多的触发条件
        adapter.setOnLoadMoreListener(this);//加载更多回调监听
        adapter.setLoadingView(LayoutInflater.from(context).inflate(R.layout.layout_live_loading, (ViewGroup) recyclerView.getParent(), false));
        adapter.setEmptyView(LayoutInflater.from(context).inflate(R.layout.layout_live_empty, (ViewGroup) recyclerView.getParent(), false));
        adapter.setLoadMoreFailedView(LayoutInflater.from(context).inflate(R.layout.layout_loadmore_error, (ViewGroup) recyclerView.getParent(), false));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int pos) {
                LiveListItemBean liveItemBean = adapter.getData().get(pos);
                Intent intent = new Intent(getActivity(), LivePlayActivity.class);
                intent.putExtra(LivePlayActivity.LIVE_TYPE, liveItemBean.getLive_type());
                intent.putExtra(LivePlayActivity.LIVE_ID, liveItemBean.getLive_id());
                intent.putExtra(LivePlayActivity.GAME_TYPE, liveItemBean.getGame_type());
                startActivity(intent);
            }
        });

    }

    @Override
    public void updateRecyclerView(List<LiveListItemBean> list) {

        refreshLayout.setRefreshing(false);
        adapter.addData(list);//在roomBeanList的尾部添加
        offset = adapter.getData().size();
        if (list.size() < LiveAPI.LIMIT){//分页数据size比每页数据的limit小，说明已全部加载数据
            adapter.loadComplete();//下一次不再加载更多，并显示FooterView
            adapter.addFooterView(LayoutInflater.from(context).inflate(R.layout.layout_footer, (ViewGroup) recyclerView.getParent(), false));
            return;
        }
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        adapter.showLoadMoreFailedView();//在加载失败的时候调用showLoadMoreFailedView()就能显示加载失败的footer了，点击footer会重新加载
        ToastUtil.show(message);
    }

    @Override
    public void onRefresh() {
        offset = 0;//重置偏移量
        liveList.clear();//清空原数据
        adapter.setNewData(liveList);
        adapter.removeAllFooterView();
        refreshLayout.setRefreshing(true);
        //根据game_type分类请求直播数据
        presenter.getLiveList(offset, LiveAPI.LIMIT, game_type );
    }

    @Override
    public void onLoadMoreRequested() {
        //根据game_type分类请求直播数据
        presenter.getLiveList(offset, LiveAPI.LIMIT, game_type );
    }
}
