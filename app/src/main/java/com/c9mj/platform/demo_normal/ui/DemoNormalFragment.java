package com.c9mj.platform.demo_normal.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.c9mj.platform.R;
import com.c9mj.platform.demo_normal.mvp.presenter.impl.DemoNormalPresenterImpl;
import com.c9mj.platform.demo_normal.mvp.view.IDemoNormalView;
import com.c9mj.platform.widget.fragment.BaseFragment;

import butterknife.ButterKnife;

public class DemoNormalFragment extends BaseFragment implements IDemoNormalView {

    private static final String KEY = "key";

    private Context context;
    private DemoNormalPresenterImpl presenter;

    public static DemoNormalFragment newInstance() {
        return newInstance("");
    }

    private static DemoNormalFragment newInstance(String game_type) {
        DemoNormalFragment fragment = new DemoNormalFragment();
        Bundle args = new Bundle();
        args.putString(KEY, game_type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_normal, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        initView();

        return attachToSwipeBack(view);
    }

    private void initView() {
        //初始化MVP
        presenter = new DemoNormalPresenterImpl(this);

        //设置其他View
    }

    @Override
    public void showError(String message) {
        ToastUtils.showShortToast(message);
    }


}
