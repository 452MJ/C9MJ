package com.c9mj.platform.live.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.widget.fragment.LazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class LiveFragment extends LazyFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static LiveFragment newInstance() {
        LiveFragment fragment = new LiveFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void initToolBar() {
        toolbar.setTitle(getString(R.string.title_live));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initToolBar();
    }
}
