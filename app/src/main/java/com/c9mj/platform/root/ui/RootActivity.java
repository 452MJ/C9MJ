package com.c9mj.platform.root.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.c9mj.platform.R;
import com.c9mj.platform.explore.ui.ExploreFragment;
import com.c9mj.platform.live.ui.LiveFragment;
import com.c9mj.platform.main.ui.MainFragment;
import com.c9mj.platform.user.ui.UserFragment;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.util.adapter.FragmentAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class RootActivity extends SupportActivity {


    long exitTime;//用于按两次Back键退出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        loadRootFragment(R.id.layout_container, MainFragment.newInstance());
    }


    @Override
    public void onBackPressedSupport() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            exitTime = System.currentTimeMillis();
            ToastUtil.show(getString(R.string.second_exit));
//            Toast.makeText(this, getString(R.string.second_exit), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressedSupport();
        }
    }

}
