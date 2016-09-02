package com.c9mj.platform.main.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.c9mj.platform.R;
import com.c9mj.platform.explore.view.NewsFragment;
import com.c9mj.platform.live.view.LiveFragment;
import com.c9mj.platform.main.adapter.MainFragmentPagerAdapter;
import com.c9mj.platform.user.view.UserFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.SimpleViewPagerDelegate;
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
    private long mExitTime;//用于按两次Back键退出
    private List<Fragment> fragmentList = new ArrayList<>();

    final int[] normalResId = new int[]{
            R.drawable.ic_explore_normal_40dp,
            R.drawable.ic_live_normal_40dp,
            R.drawable.ic_user_normal_40dp
    };
    final int[] pressedResId = new int[]{
            R.drawable.ic_explore_pressed_40dp,
            R.drawable.ic_live_pressed_40dp,
            R.drawable.ic_user_pressed_40dp
    };

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
        initIndicator();

    }




    private void initIndicator() {
        final CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setFollowTouch(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context);
                titleView.setContentView(R.layout.item_tab_layout);

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
                indicator.setMaxCircleRadius(UIUtil.dip2px(context, 2.5));
                indicator.setColors(getResources().getColor(R.color.color_primary));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        SimpleViewPagerDelegate.with(magicIndicator, viewPager).delegate();
    }

    private void initViewPager() {
        fragmentList.add(new NewsFragment());
        fragmentList.add(new LiveFragment());
        fragmentList.add(new UserFragment());
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressedSupport() {
        if (System.currentTimeMillis() - mExitTime > 2000){
            mExitTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.second_exit), Toast.LENGTH_SHORT).show();
        }else{
            super.onBackPressedSupport();
        }
    }
}
