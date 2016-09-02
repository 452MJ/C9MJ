package com.c9mj.platform.explore.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class NewsFragment extends SupportFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
