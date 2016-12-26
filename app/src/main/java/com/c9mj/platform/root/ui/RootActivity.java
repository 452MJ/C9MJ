package com.c9mj.platform.root.ui;

import android.os.Bundle;

import com.c9mj.platform.R;
import com.c9mj.platform.main.ui.MainFragment;
import com.c9mj.platform.widget.activity.BaseActivity;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class RootActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        loadRootFragment(R.id.layout_container, MainFragment.newInstance());
    }
}
