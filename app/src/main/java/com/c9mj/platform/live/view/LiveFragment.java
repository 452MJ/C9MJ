package com.c9mj.platform.live.view;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c9mj.platform.R;
import com.c9mj.platform.util.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class LiveFragment extends SupportFragment {

    private static LiveFragment fragment = null;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.text)
    TextView text;

    public static LiveFragment newInstance() {
        if (fragment == null) {
            synchronized (LiveFragment.class) {
                fragment = new LiveFragment();
            }
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_live, container, false);
        ButterKnife.bind(this, view);

        initToolBar();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initToolBar() {
        toolbar.setTitle(getString(R.string.title_live));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        text.setText(DateUtil.timeStamp2Date(String.valueOf(System.currentTimeMillis()), null));
        Log.e("live", "initToolBar: ");
    }
}
