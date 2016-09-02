package com.c9mj.platform.main.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.c9mj.platform.R;
import com.c9mj.platform.explore.view.ExploreFragment;
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
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class MainActivity extends SupportActivity {

    public int TAB_INDEX;
    public static final int EXPLORE = 0;
    public static final int LIVE = 1;
    public static final int USER = 2;


    private long mExitTime;//用于按两次Back键退出
    private List<SupportFragment> fragmentList = new ArrayList<>();
    private SupportFragment[] fragmentArray = new SupportFragment[3];

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

        initFragment(savedInstanceState);
        initViewPager();
        initIndicator();

    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
//            fragmentArray[EXPLORE] = ExploreFragment.newInstance();
//            fragmentArray[LIVE] = LiveFragment.newInstance();
//            fragmentArray[USER] = UserFragment.newInstance();
//
//            loadMultipleRootFragment(R.id.viewpager, EXPLORE,
//                    fragmentArray[EXPLORE],
//                    fragmentArray[LIVE],
//                    fragmentArray[USER]
//            );
//            TAB_INDEX = EXPLORE;

            fragmentList.add(ExploreFragment.newInstance());
            fragmentList.add(LiveFragment.newInstance());
            fragmentList.add(UserFragment.newInstance());
        } else {
            // 这里库已经做了Fragment恢复工作，不需要额外的处理
            // 这里我们需要拿到mFragments的引用，用下面的方法查找更方便些，也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些)
//            fragmentArray[EXPLORE] = findFragment(ExploreFragment.class);
//            fragmentArray[LIVE] = findFragment(LiveFragment.class);
//            fragmentArray[USER] = findFragment(UserFragment.class);

            fragmentList.clear();
            fragmentList.add(findFragment(ExploreFragment.class));
            fragmentList.add(findFragment(LiveFragment.class));
            fragmentList.add(findFragment(UserFragment.class));
        }
    }

    private void initViewPager() {
//        ExploreFragment exploreFragment = ExploreFragment.newInstance();
//        LiveFragment liveFragment = LiveFragment.newInstance();
//        UserFragment userFragment = UserFragment.newInstance();
//        fragmentList.add(exploreFragment);
//        fragmentList.add(liveFragment);
//        fragmentList.add(userFragment);
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
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
//                        switch (index) {
//                            case EXPLORE: {//jump to ExploreFragment
//                                if (TAB_INDEX == LIVE)
//                                    showHideFragment(fragmentArray[EXPLORE], fragmentArray[LIVE]);
//                                if (TAB_INDEX == USER)
//                                    showHideFragment(fragmentArray[EXPLORE], fragmentArray[USER]);
//                            }
//                            break;
//                            case LIVE: {//jump to LiveFragment
//                                if (TAB_INDEX == EXPLORE)
//                                    showHideFragment(fragmentArray[LIVE], fragmentArray[EXPLORE]);
//                                if (TAB_INDEX == USER)
//                                    showHideFragment(fragmentArray[LIVE], fragmentArray[USER]);
//                            }
//                            break;
//                            case USER: {//jump to UserFrament
//                                if (TAB_INDEX == EXPLORE)
//                                    showHideFragment(fragmentArray[USER], fragmentArray[EXPLORE]);
//                                if (TAB_INDEX == LIVE)
//                                    showHideFragment(fragmentArray[USER], fragmentArray[LIVE]);
//                            }
//                            break;
//                            default:
//                                break;
//                        }
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


    @Override
    public void onBackPressedSupport() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.second_exit), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressedSupport();
        }
    }
}
