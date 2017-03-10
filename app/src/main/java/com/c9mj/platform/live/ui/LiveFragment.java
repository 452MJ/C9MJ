package com.c9mj.platform.live.ui;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.live.adapter.LiveTypeAdapter;
import com.c9mj.platform.util.global.FragmentAdapter;
import com.c9mj.platform.widget.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

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
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: LMJ
 * date: 2016/9/1
 * 直播主页面
 */
public class LiveFragment extends BaseFragment {

    private final List<String> typeIdList = new ArrayList<>();    //直播平台id
    private final List<String> typeNameList = new ArrayList<>();  //直播平台名字
    private final Integer[] logoArrays = new Integer[]{
            0,
            R.drawable.logo_douyu,
            R.drawable.logo_panda,
            R.drawable.logo_zhanqi,
            R.drawable.logo_yy,
            R.drawable.logo_longzhu,
            R.drawable.logo_quanmin,
            R.drawable.logo_cc,
            R.drawable.logo_huomao
    };
    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> titleList = new ArrayList<>();
    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tv_live_type)
    TextView tv_live_type;
    private int pos;
    private int currentPos;
    private Context context;
    private CommonNavigatorAdapter navigatorAdapter;
    private FragmentAdapter fragmentAdapter;

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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initView();
    }

    private void initView() {
        //初始化MVP

        //设置RefreshLayout

        //设置RecyclerView

        /***设置其他View***/
        //直播平台ID
        Collections.addAll(typeIdList, context.getResources().getStringArray(R.array.live_type_id));
        //直播平台名称
        Collections.addAll(typeNameList, context.getResources().getStringArray(R.array.live_type_name));
        //顶部栏标题
        titleList.add(getString(R.string.live_lol));
        titleList.add(getString(R.string.live_ow));
        titleList.add(getString(R.string.live_dota2));
        titleList.add(getString(R.string.live_hs));
        titleList.add(getString(R.string.live_csgo));
        fragmentList.add(LiveListFragment.newInstance(getString(R.string.game_type_lol)));
        fragmentList.add(LiveListFragment.newInstance(getString(R.string.game_type_ow)));
        fragmentList.add(LiveListFragment.newInstance(getString(R.string.game_type_dota2)));
        fragmentList.add(LiveListFragment.newInstance(getString(R.string.game_type_hs)));
        fragmentList.add(LiveListFragment.newInstance(getString(R.string.game_type_csgo)));
        //ViewPager + Indicator
        fragmentAdapter = new FragmentAdapter(this.getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(4);

        CommonNavigator navigator = new CommonNavigator(context);
        navigator.setAdjustMode(true);
        navigator.setFollowTouch(true);
        navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                CommonPagerTitleView titleView = new CommonPagerTitleView(context);
                titleView.setContentView(R.layout.item_live_tab_indicator_layout);//加载自定义布局作为Tab

                final TextView tab_textview = (TextView) titleView.findViewById(R.id.tab_text);
                tab_textview.setText(titleList.get(index));
                titleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {
                    }

                    @Override
                    public void onDeselected(int i, int i1) {

                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });
                titleView.setOnClickListener(v -> viewPager.setCurrentItem(index));

                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setYOffset(UIUtil.dip2px(context, 0.5));
                indicator.setColors(ContextCompat.getColor(context, R.color.color_icons));
                return indicator;
            }
        };

        navigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(navigator);
        ViewPagerHelper.bind(indicator, viewPager);
    }

    @OnClick(R.id.tv_live_type)
    public void onClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View contentView = View.inflate(context, R.layout.layout_live_type_picker, null);
        final ImageView iv_logo = (ImageView) contentView.findViewById(R.id.layout_live_type_picker_iv_logo);
        RecyclerView recylcerView = (RecyclerView) contentView.findViewById(R.id.layout_live_type_picker_recyclerview);
        LiveTypeAdapter adapter = new LiveTypeAdapter(typeNameList);
        recylcerView.setLayoutManager(new GridLayoutManager(context, 3));
        recylcerView.setAdapter(adapter);
        recylcerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Glide.with(context)
                        .load(logoArrays[position])
                        .dontAnimate()
                        .into(iv_logo);

                iv_logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                currentPos = position;
            }
        });

        builder.setView(contentView)
                .setPositiveButton(getString(R.string.enter), (dialog, which) -> {
                    pos = currentPos;
                    tv_live_type.setText(typeNameList.get(pos));
                    dialog.dismiss();
                })
                .setNegativeButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.color_primary));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.color_secondary_text));

    }

    /**
     * 暴露给内嵌子Fragment获取直播平台id
     *
     * @return
     */
    public String getLiveType() {
        return typeIdList.get(pos);
    }
}
