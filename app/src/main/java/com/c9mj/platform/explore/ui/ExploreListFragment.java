package com.c9mj.platform.explore.ui;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.explore.adapter.ExploreListAdapter;
import com.c9mj.platform.explore.api.ExploreAPI;
import com.c9mj.platform.explore.mvp.model.ExploreListItemBean;
import com.c9mj.platform.explore.mvp.presenter.impl.ExploreListPresenterImpl;
import com.c9mj.platform.explore.mvp.view.IExploreListFragment;
import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.ui.LivePlayActivity;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.widget.animation.CustionAnimation;
import com.c9mj.platform.widget.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * author: LMJ
 * date: 2016/9/19
 * 直播列表
 */
public class ExploreListFragment extends BaseFragment implements IExploreListFragment,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private static final String EXPLORE_TYPE_ID = "explore_type_id";

    String explore_type_id;
    int offset = 0;//用于记录分页偏移量
    List<ExploreListItemBean> exploreList = new ArrayList<>();

    Context context;
    ExploreListPresenterImpl presenter;

    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    ExploreListAdapter adapter;

    public static ExploreListFragment newInstance() {
        return newInstance("");
    }

    public static ExploreListFragment newInstance(String explore_type_id) {
        ExploreListFragment fragment = new ExploreListFragment();
        Bundle args = new Bundle();
        args.putString(EXPLORE_TYPE_ID, explore_type_id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_list, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        explore_type_id = getArguments().getString(EXPLORE_TYPE_ID);//得到传入的explore_type_id

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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        refreshLayout.setProgressViewOffset(false, 0, 30);// 这句话是为了，第一次进入页面初始化数据的时候显示加载进度条
        refreshLayout.setRefreshing(true);
        //根据game_type分类请求直播数据
        presenter.getExploreList(explore_type_id, offset, LiveAPI.LIMIT);
    }



    private void initMVP() {
        presenter = new ExploreListPresenterImpl(this);
    }

    private void initRefreshView() {
        refreshLayout.setColorSchemeResources(R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new ExploreListAdapter(exploreList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.openLoadAnimation(new CustionAnimation());
        adapter.isFirstOnly(true);
        adapter.openLoadMore(ExploreAPI.LIMIT);//加载更多的触发条件
        adapter.setOnLoadMoreListener(this);//加载更多回调监听
        adapter.setLoadingView(LayoutInflater.from(context).inflate(R.layout.layout_explore_loading, (ViewGroup) recyclerView.getParent(), false));
        adapter.setEmptyView(LayoutInflater.from(context).inflate(R.layout.layout_explore_empty, (ViewGroup) recyclerView.getParent(), false));
        adapter.setLoadMoreFailedView(LayoutInflater.from(context).inflate(R.layout.layout_loadmore_error, (ViewGroup) recyclerView.getParent(), false));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ExploreListItemBean exploreItemBean = adapter.getData().get(i);
                Intent intent = new Intent(getActivity(), LivePlayActivity.class);
//                intent.putExtra(LivePlayActivity.LIVE_TYPE, liveItemBean.getLive_type());
//                intent.putExtra(LivePlayActivity.LIVE_ID, liveItemBean.getLive_id());
//                intent.putExtra(LivePlayActivity.GAME_TYPE, liveItemBean.getGame_type());
                startActivity(intent);
            }
        });

    }

    @Override
    public void updateRecyclerView(List<ExploreListItemBean> list) {
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
        exploreList.clear();//清空原数据
        adapter.setNewData(exploreList);
        adapter.removeAllFooterView();
        refreshLayout.setRefreshing(true);
        //根据explore_type_id请求更多新闻数据
        presenter.getExploreList(explore_type_id, offset, ExploreAPI.LIMIT);
    }

    @Override
    public void onLoadMoreRequested() {
        //根据explore_type_id请求更多新闻数据
        presenter.getExploreList(explore_type_id, offset, ExploreAPI.LIMIT);
    }
}
