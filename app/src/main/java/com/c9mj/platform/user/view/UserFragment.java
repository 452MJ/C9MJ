package com.c9mj.platform.user.view;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.c9mj.platform.R;
import com.c9mj.platform.widget.LazyFragment;
import com.commit451.nativestackblur.NativeStackBlur;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class UserFragment extends LazyFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.app_bar_iv)
    ImageView appBarIv;


    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    private void initToolBar() {
        toolbar.setTitle(getString(R.string.title_user));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        appBarIv.setImageBitmap(NativeStackBlur.process(BitmapFactory.decodeResource(getResources(), R.drawable.ic_header), 40));

    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initToolBar();
    }
}
