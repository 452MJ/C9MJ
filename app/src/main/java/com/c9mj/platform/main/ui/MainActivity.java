package com.c9mj.platform.main.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.c9mj.platform.R;
import com.c9mj.platform.explore.ui.ExploreFragment;
import com.c9mj.platform.live.ui.LiveFragment;
import com.c9mj.platform.util.SnackbarUtil;
import com.c9mj.platform.util.adapter.FragmentAdapter;
import com.c9mj.platform.user.ui.UserFragment;
import com.c9mj.platform.widget.activity.BaseActivity;

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
public class MainActivity extends SupportActivity {

    public int TAB_INDEX;
    public static final int EXPLORE = 0;
    public static final int LIVE = 1;
    public static final int USER = 2;


    private long exitTime;//用于按两次Back键退出
    private List<Fragment> fragmentList = new ArrayList<>();

    final int[] normalResId = new int[]{
            R.drawable.ic_explore_normal,
            R.drawable.ic_live_normal,
            R.drawable.ic_user_normal
    };
    final int[] pressedResId = new int[]{
            R.drawable.ic_explore_pressed,
            R.drawable.ic_live_pressed,
            R.drawable.ic_user_pressed
    };

    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SnackbarUtil.init(getWindow().getDecorView());

        initFragment();
        initViewPager();
        initIndicator();

    }

    private void initFragment() {
        fragmentList.add(ExploreFragment.newInstance());
        fragmentList.add(LiveFragment.newInstance());
        fragmentList.add(UserFragment.newInstance());
    }

    private void initViewPager() {
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
    }


    private void initIndicator() {
        CommonNavigator navigator = new CommonNavigator(this);
        navigator.setAdjustMode(true);
        navigator.setFollowTouch(true);
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context);
                titleView.setContentView(R.layout.item_main_tab_indicator_layout);//加载自定义布局作为Tab

                final Button tab_btn = (Button) titleView.findViewById(R.id.tab_btn);
                titleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {
                        tab_btn.setBackgroundResource(pressedResId[i]);
                    }

                    @Override
                    public void onDeselected(int i, int i1) {
                        tab_btn.setBackgroundResource(normalResId[i]);
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setMaxCircleRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(getResources().getColor(R.color.color_primary));
                return indicator;
            }
        });
        indicator.setNavigator(navigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }


    @Override
    public void onBackPressedSupport() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            exitTime = System.currentTimeMillis();
            SnackbarUtil.show(getString(R.string.second_exit));
//            Toast.makeText(this, getString(R.string.second_exit), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressedSupport();
        }
    }

}
