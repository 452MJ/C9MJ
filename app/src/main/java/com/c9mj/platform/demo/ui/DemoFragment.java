package com.c9mj.platform.demo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.demo.adapter.DemoAdapter;
import com.c9mj.platform.demo.mvp.model.bean.DemoBean;
import com.c9mj.platform.demo.mvp.presenter.impl.DemoPresenterImpl;
import com.c9mj.platform.demo.mvp.view.IDemoView;
import com.c9mj.platform.widget.animation.CustionAnimation;
import com.c9mj.platform.widget.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 快速构建MVP的模板
 * Created by Administrator on 2016/11/16.
 */

public class DemoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, IDemoView {

    private static final String KEY = "key";

    private Context context;

    private DemoPresenterImpl presenter;

    @BindView(R.id.layout_refresh)
    SwipeRefreshLayout layout_refresh;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private final List<DemoBean> list = new ArrayList<>();
    private DemoAdapter adapter;


    public static DemoFragment newInstance() {
        return newInstance("");
    }

    private static DemoFragment newInstance(String game_type) {
        DemoFragment fragment = new DemoFragment();
        Bundle args = new Bundle();
        args.putString(KEY, game_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        initView();

        return attachToSwipeBack(view);
    }

    private void initView() {
        //初始化MVP
        presenter = new DemoPresenterImpl(this);

        //设置RefreshLayout
        layout_refresh.setColorSchemeResources(R.color.color_primary);
        layout_refresh.setOnRefreshListener(this);

        //设置RecyclerView
        for (int i = 0; i < 5; i++) {
            list.add(new DemoBean());
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new DemoAdapter(list);
        adapter.openLoadAnimation(new CustionAnimation());
        adapter.isFirstOnly(true);

        //footerView
        View footerView = LayoutInflater.from(context).inflate(R.layout.layout_footer, (ViewGroup) recyclerView.getParent(), false);
//        TextView tv_footer = (TextView) footerView.findViewById(R.id.tv_footer);
//        tv_footer.setTextColor(context.getResources().getColor(R.color.color_text_black));
        adapter.addFooterView(footerView);

        //emptyView
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, (ViewGroup) recyclerView.getParent(), false);
//        TextView tv_empty = (TextView) emptyView.findViewById(R.id.tv_empty);
//        tv_empty.setTextColor(context.getResources().getColor(R.color.color_text_black));  recyclerView.setAdapter(adapter);
        adapter.setEmptyView(emptyView);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuikAdapter, View view, int pos) {
                switch (view.getId()) {

                }
            }
        });
        //设置其他View
    }

    @Override
    public void onRefresh() {

        list.clear();
        for (int i = 0; i < 5; i++) {
            list.add(new DemoBean());
        }
        adapter.setNewData(list);
        layout_refresh.setRefreshing(false);
    }

    @Override
    public void showError(String message) {

    }


}
