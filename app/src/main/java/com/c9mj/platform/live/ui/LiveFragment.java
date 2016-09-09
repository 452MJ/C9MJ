package com.c9mj.platform.live.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c9mj.platform.R;
import com.c9mj.platform.live.bean.LiveIndicatorBean;
import com.c9mj.platform.live.mvp.presenter.impl.LivePresenterImpl;
import com.c9mj.platform.live.mvp.view.ILiveFragment;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.util.adapter.FragmentAdapter;
import com.c9mj.platform.widget.fragment.LazyFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: LMJ
 * date: 2016/9/1
 * 直播主页面
 */
public class LiveFragment extends LazyFragment implements ILiveFragment {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<LiveIndicatorBean> columnList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private Context context;
    private LivePresenterImpl presenter;
    private FragmentAdapter fragmentAdapter;
    private CommonNavigatorAdapter navigatorAdapter;

    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

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

        context = view.getContext();

        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initMVP();
        initFragment();
        initViewPager();
        initIndicator();
    }

    private void initMVP() {
        presenter = new LivePresenterImpl(context, this);
        presenter.getColumnList();
    }

    private void initFragment() {
        fragmentList.add(LiveRoomListFragment.newInstance());
        titleList.add(getString(R.string.live_all));
    }

    private void initViewPager() {
        fragmentAdapter = new FragmentAdapter(this.getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(10);
    }

    private void initIndicator() {
        CommonNavigator navigator = new CommonNavigator(context);
        navigator.setAdjustMode(false);
        navigator.setFollowTouch(true);
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setText(titleList.get(index));
                titleView.setNormalColor(getResources().getColor(R.color.color_tab_normal_dark));
                titleView.setSelectedColor(getResources().getColor(R.color.color_tab_pressed_dark));
                titleView.setTextSize(12);
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
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setYOffset(UIUtil.dip2px(context, 0.5));
                indicator.setColors(getResources().getColor(R.color.color_icons));
                return indicator;
            }
        };

        navigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(navigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    @Override
    public void updateIndicator(List<LiveIndicatorBean> list) {
        for (LiveIndicatorBean bean :
                list) {
            fragmentList.add(LiveRoomListFragment.newInstance());
            titleList.add(bean.getCate_name());
        }
        fragmentAdapter.notifyDataSetChanged();
        navigatorAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        ToastUtil.show(context, message);
    }
}
