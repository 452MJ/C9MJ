package com.c9mj.platform.explore.ui;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.c9mj.platform.R;
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
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class ExploreFragment extends LazyFragment {

    private String[] idArray;
    private String[] aliasArray;
    private String[] enameArray;
    private String[] tnameArray;
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    private Context context;

    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
    CommonNavigatorAdapter navigatorAdapter;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;

    @BindView(R.id.explore_tv_section)
    TextView exploreTvSection;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.explore_iv_expand)
    ImageView exploreIvExpand;
    boolean isExpanded = false;

    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        initData();
        initFragment();
        initViewPager();
        initIndicator();

        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {

    }

    private void initData() {
        idArray = context.getResources().getStringArray(R.array.explore_type_id);
        aliasArray = context.getResources().getStringArray(R.array.explore_type_alias);
        enameArray = context.getResources().getStringArray(R.array.explore_type_ename);
        tnameArray = context.getResources().getStringArray(R.array.explore_type_tname);

        exploreIvExpand.setImageResource(isExpanded ? R.drawable.ic_expand_close : R.drawable.ic_expand_open);

        exploreTvSection.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ObjectAnimator animator0 = ObjectAnimator.ofFloat(exploreTvSection, "translationX", exploreTvSection.getWidth());
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(scrollView, "translationY", -scrollView.getHeight());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator0, animator1);
                animatorSet.setDuration(0);
                animatorSet.start();

                exploreTvSection.getViewTreeObserver().removeGlobalOnLayoutListener(this);//得到后取消监听
            }
        });

    }

    private void initFragment() {
        for (int i = 0; i < tnameArray.length; i++) {
            titleList.add(tnameArray[i]);
        }
        for (int i = 0; i < tnameArray.length; i++) {
            fragmentList.add(ExploreListFragment.newInstance(getString(R.string.game_type_lol)));
        }
    }

    private void initViewPager() {
        fragmentAdapter = new FragmentAdapter(this.getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    private void initIndicator() {
        CommonNavigator navigator = new CommonNavigator(context);
        navigator.setAdjustMode(false);
        navigator.setScrollPivotX(0.2f);
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList == null ? 0 : fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(final Context context, final int index) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context);
                titleView.setContentView(R.layout.item_explore_tab_indicator_layout);//加载自定义布局作为Tab

                final TextView tab_textview = (TextView) titleView.findViewById(R.id.tab_text);
                titleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {
                        tab_textview.setText(tnameArray[i]);
                        tab_textview.setTextColor(context.getResources().getColor(R.color.color_primary));
                    }

                    @Override
                    public void onDeselected(int i, int i1) {
                        tab_textview.setText(tnameArray[i]);
                        tab_textview.setTextColor(context.getResources().getColor(R.color.color_secondary_text));
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
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setYOffset(UIUtil.dip2px(context, 0.5));
                indicator.setColors(getResources().getColor(R.color.color_primary));
                return indicator;
            }
        };
        navigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(navigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    @OnClick({
            R.id.explore_iv_expand,
            R.id.explore_tv_section,
            R.id.scroll_view
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.explore_iv_expand:
                isExpanded = !isExpanded;
                exploreIvExpand.setImageResource(isExpanded ? R.drawable.ic_expand_close : R.drawable.ic_expand_open);

                ObjectAnimator animator0 = ObjectAnimator.ofFloat(exploreTvSection, "translationX", isExpanded ? 0 : exploreTvSection.getWidth());

                ObjectAnimator animator1 = ObjectAnimator.ofFloat(scrollView, "translationY", isExpanded ? 0 : -scrollView.getHeight());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator0, animator1);
                animatorSet.setDuration(500);
                animatorSet.setInterpolator(new BounceInterpolator());
                animatorSet.start();
                break;

            case R.id.explore_tv_section:
                break;
            case R.id.scroll_view:
                break;
        }
    }
}
