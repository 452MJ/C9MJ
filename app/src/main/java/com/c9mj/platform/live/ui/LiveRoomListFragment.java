package com.c9mj.platform.live.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.live.bean.LiveIndicatorBean;
import com.c9mj.platform.live.mvp.presenter.impl.LiveRoomListPresenterImpl;
import com.c9mj.platform.live.mvp.view.ILiveRoomListFragment;
import com.c9mj.platform.widget.fragment.LazyFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LiveRoomListFragment extends LazyFragment implements ILiveRoomListFragment {

    private static final String CATE_ID = "cate_id";
    private String cate_id;

    private Context context;
    private LiveRoomListPresenterImpl presenter;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    public static LiveRoomListFragment newInstance() {
        return newInstance("");
    }
    public static LiveRoomListFragment newInstance(String cate_id) {
        LiveRoomListFragment fragment = new LiveRoomListFragment();
        Bundle args = new Bundle();
        args.putString(CATE_ID, cate_id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live_roomlist, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();
        cate_id = getArguments().getString(CATE_ID);//得到传入的cate_id

        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initMVP();
        initRecyclerView();
    }

    private void initMVP() {
        presenter = new LiveRoomListPresenterImpl(context, this);
    }

    private void initRecyclerView() {

    }

    @Override
    public void updateRecyclerView(List<LiveIndicatorBean> columnBeanList) {

    }

    @Override
    public void showError(String message) {

    }
}
