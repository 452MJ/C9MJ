package com.c9mj.platform.demo_list.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.c9mj.platform.R;
import com.c9mj.platform.demo_list.adapter.DemoListAdapter;
import com.c9mj.platform.demo_list.mvp.model.bean.DemoListBean;
import com.c9mj.platform.demo_list.mvp.presenter.impl.DemoListPresenterImpl;
import com.c9mj.platform.demo_list.mvp.view.IDemoListView;
import com.c9mj.platform.util.ProgressUtil;
import com.c9mj.platform.widget.fragment.BaseFragment;
import com.c9mj.platform.widget.recyclerview.CustomLoadMoreView;
import com.c9mj.platform.widget.recyclerview.animation.CustomAnimation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DemoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IDemoListView, BaseQuickAdapter.RequestLoadMoreListener {

    private static final String KEY = "key";
    private final List<DemoListBean> list = new ArrayList<>();

    @BindView(R.id.toolbar_iv_back)
    ImageView toolbarIvBack;
    @BindView(R.id.toolbar_tv_title)
    TextView toolbarTvTitle;
    @BindView(R.id.layout_toolbar)
    RelativeLayout layoutToolbar;
    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private Context context;
    private DemoListPresenterImpl presenter;
    private DemoListAdapter adapter;

    public static DemoListFragment newInstance() {
        return newInstance("");
    }

    private static DemoListFragment newInstance(String game_type) {
        DemoListFragment fragment = new DemoListFragment();
        Bundle args = new Bundle();
        args.putString(KEY, game_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_list, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        initView();

        return attachToSwipeBack(view);
    }

    private void initView() {
        //初始化MVP
        presenter = new DemoListPresenterImpl(this);

        //设置TitleBar
        layoutToolbar.setBackgroundColor(ContextCompat.getColor(context, R.color.color_primary));
        toolbarIvBack.setVisibility(View.VISIBLE);
        toolbarTvTitle.setVisibility(View.VISIBLE);
        toolbarTvTitle.setText(getString(R.string.app_name));

        //设置RefreshLayout
        layout_refresh.setColorSchemeResources(R.color.color_primary);
        layout_refresh.setOnRefreshListener(this);

        //设置RecyclerView
        for (int i = 0; i < 5; i++) {
            list.add(new DemoListBean());
        }

        adapter = new DemoListAdapter(list);
        adapter.openLoadAnimation(new CustomAnimation());
        adapter.isFirstOnly(true);

        //emptyView
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, (ViewGroup) recyclerView.getParent(), false);
//        TextView tv_empty = (TextView) emptyView.findViewById(R.id.tv_empty);
//        tv_empty.setTextColor(context.getResources().getColor(R.color.color_text_black));  recyclerView.setAdapter(adapter);
        adapter.setEmptyView(emptyView);
        //loadMore
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setOnLoadMoreListener(this);
        //添加分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new SimpleClickListener() {

            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        //设置其他View
    }

    @Override
    public void onRefresh() {

        list.clear();
        for (int i = 0; i < 5; i++) {
            list.add(new DemoListBean());
        }
        adapter.setNewData(list);
        layout_refresh.setRefreshing(false);
    }

    @Override
    public void showError(String message) {
        ProgressUtil.dismiss();
        ToastUtils.showShortToast(message);
    }


    @Override
    public void onLoadMoreRequested() {

    }

    @OnClick({R.id.toolbar_iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_iv_back:
                pop();
                break;
            default:
                break;
        }
    }
}
