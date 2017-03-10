package com.c9mj.platform.explore.ui;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.utils.SizeUtils;
import com.c9mj.platform.App;
import com.c9mj.platform.R;
import com.c9mj.platform.explore.adapter.ExploreSelectedTitleListAdapter;
import com.c9mj.platform.explore.adapter.ExploreUnSelectedTitleListAdapter;
import com.c9mj.platform.util.global.FragmentAdapter;
import com.c9mj.platform.util.global.Constants;
import com.c9mj.platform.widget.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
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
 */
public class ExploreFragment extends BaseFragment implements OnItemDragListener, OnItemSwipeListener {


    private final List<Fragment> fragmentList = new ArrayList<>();
    @BindView(R.id.magic_indicator)
    MagicIndicator indicator;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    //用于展开显示栏目切换
    @BindView(R.id.tv_section)
    TextView tv_section;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.iv_expandable)
    ImageView iv_expandable;
    //栏目切换的Top列表
    @BindView(R.id.recyclerview_selected)
    RecyclerView rv_selected;
    //栏目切换的Bottom列表
    @BindView(R.id.recyclerview_unselected)
    RecyclerView rv_unselected;
    private String[] idArray;
    private String[] aliasArray;
    private String[] enameArray;
    private String[] tnameArray;
    private String titleString = "";//用于保存记录所有栏目
    private String selectedTitleString;//用于保存记录已选择栏目
    private String unselectedTitleString;//用于保存记录未选择栏目
    private List<String> titleList = new ArrayList<>();//所有TitleList
    private List<String> selectedTitleList = new ArrayList<>();//编辑模式下的已选择Tab的TitleList
    private List<String> unSelectedTitleList = new ArrayList<>();//编辑模式下的未选择Tab的TitleList
    private Context context;
    private CommonNavigator navigator;
    private FragmentAdapter fragmentAdapter;
    private boolean isExpanded = false;
    private ExploreSelectedTitleListAdapter selectedAdapter;
    private ExploreUnSelectedTitleListAdapter unselectedAdapter;


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

        /***设置其他View***/
        //不同栏目分组
        idArray = context.getResources().getStringArray(R.array.explore_type_id);
        aliasArray = context.getResources().getStringArray(R.array.explore_type_alias);
        enameArray = context.getResources().getStringArray(R.array.explore_type_ename);
        tnameArray = context.getResources().getStringArray(R.array.explore_type_tname);

        iv_expandable.setImageResource(isExpanded ? R.drawable.ic_expand_close : R.drawable.ic_expand_open);

        tv_section.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ObjectAnimator animator0 = ObjectAnimator.ofFloat(tv_section, "translationX", tv_section.getWidth());
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(scrollView, "translationY", -scrollView.getHeight());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator0, animator1);
                animatorSet.setDuration(0);
                animatorSet.start();

                tv_section.getViewTreeObserver().removeOnGlobalLayoutListener(this);//得到后取消监听
            }
        });

        if (TextUtils.isEmpty(App.getSpUtils().getString(Constants.STRING_TITLE))) {
            for (String aTnameArray : tnameArray) {
                titleString = titleString + aTnameArray + ":";
            }
            App.getSpUtils().putString(Constants.STRING_TITLE, titleString);
        } else {
            titleString = App.getSpUtils().getString(Constants.STRING_TITLE);
        }

        //得到所保存的Seclected&UnSelected的Title信息
        selectedTitleString = App.getSpUtils().getString(Constants.STRING_TITLE_SELECTED);
        unselectedTitleString = App.getSpUtils().getString(Constants.STRING_TITLE_UNSELECTED);

        //初始化，已选择栏目&未选择栏目
        if (TextUtils.isEmpty(selectedTitleString) || TextUtils.isEmpty(unselectedTitleString)) {

            selectedTitleString = unselectedTitleString = "";

            App.getSpUtils().putString(Constants.STRING_TITLE_SELECTED, "");
            App.getSpUtils().putString(Constants.STRING_TITLE_UNSELECTED, "");

            for (int i = 0; i < tnameArray.length; i++) {
                if (i < tnameArray.length / 2) {
                    selectedTitleString = selectedTitleString + tnameArray[i] + ":";
                } else {
                    unselectedTitleString = unselectedTitleString + tnameArray[i] + ":";
                }
            }
            App.getSpUtils().putString(Constants.STRING_TITLE_SELECTED, selectedTitleString);
            App.getSpUtils().putString(Constants.STRING_TITLE_UNSELECTED, unselectedTitleString);
        }

        //Title之间以:进行区分， 得到List
        titleList = parseStringToListByColons(titleString);
        selectedTitleList = parseStringToListByColons(selectedTitleString);
        unSelectedTitleList = parseStringToListByColons(unselectedTitleString);
        //设置栏目RecyclerView
        selectedAdapter = new ExploreSelectedTitleListAdapter(selectedTitleList);
        unselectedAdapter = new ExploreUnSelectedTitleListAdapter(unSelectedTitleList);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(selectedAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(rv_selected);
        // 开启拖拽
        selectedAdapter.enableDragItem(itemTouchHelper, R.id.cardview, true);
        selectedAdapter.setOnItemDragListener(this);
        // 开启滑动删除
        selectedAdapter.enableSwipeItem();
        selectedAdapter.setOnItemSwipeListener(this);

        rv_unselected.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                selectedAdapter.add(selectedAdapter.getData().size(), unselectedAdapter.getItem(position));

                int index = titleList.indexOf(unSelectedTitleList.get(position));
                String explore_type_id = idArray[index];
                fragmentList.add(ExploreListFragment.newInstance(explore_type_id));

                unselectedAdapter.remove(position);

                navigator.notifyDataSetChanged();    // must call firstly
                fragmentAdapter.notifyDataSetChanged();
            }


        });

        rv_selected.setLayoutManager(new GridLayoutManager(context, 4));
        rv_unselected.setLayoutManager(new GridLayoutManager(context, 4));
        rv_selected.setAdapter(selectedAdapter);
        rv_unselected.setAdapter(unselectedAdapter);

        //ViewPager + Indicator
        for (int i = 0; i < selectedTitleList.size(); i++) {
            int index = titleList.indexOf(selectedTitleList.get(i));
            String explore_type_id = idArray[index];
            fragmentList.add(ExploreListFragment.newInstance(explore_type_id));
        }

        fragmentAdapter = new FragmentAdapter(this.getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2);

        navigator = new CommonNavigator(context);
        navigator.setAdjustMode(false);
        navigator.setIndicatorOnTop(true);
        navigator.setSkimOver(true);
        navigator.setScrollPivotX(0.2f);
        CommonNavigatorAdapter navigatorAdapter = new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return selectedTitleList == null ? 0 : selectedTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(final Context context, final int index) {

                if (Build.VERSION.SDK_INT >= 21) {
                    ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                    clipPagerTitleView.setText(selectedTitleList.get(index));
                    clipPagerTitleView.setBackground(ContextCompat.getDrawable(context, R.drawable.ripple_tab));
                    clipPagerTitleView.setTextSize(SizeUtils.sp2px(12));
                    clipPagerTitleView.setTextColor(ContextCompat.getColor(context, R.color.color_secondary_text));
                    clipPagerTitleView.setClipColor(ContextCompat.getColor(context, R.color.color_primary));
                    clipPagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index));
                    return clipPagerTitleView;
                }

                CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
                commonPagerTitleView.setContentView(R.layout.item_explore_tab_indicator_layout);//加载自定义布局作为Tab

                final TextView tab_textview = (TextView) commonPagerTitleView.findViewById(R.id.tab_text);
                tab_textview.setText(selectedTitleList.get(index));
                commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {
                    @Override
                    public void onSelected(int i, int i1) {
                        tab_textview.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
                    }

                    @Override
                    public void onDeselected(int i, int i1) {
                        tab_textview.setTextColor(ContextCompat.getColor(context, R.color.color_secondary_text));
                    }

                    @Override
                    public void onLeave(int i, int i1, float v, boolean b) {

                    }

                    @Override
                    public void onEnter(int i, int i1, float v, boolean b) {

                    }
                });
                commonPagerTitleView.setOnClickListener(v -> viewPager.setCurrentItem(index));
                return commonPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setYOffset(UIUtil.dip2px(context, 0.5));
                indicator.setColors(ContextCompat.getColor(context, R.color.color_primary));
                return indicator;
            }
        };
        navigator.setAdapter(navigatorAdapter);
        indicator.setNavigator(navigator);
        ViewPagerHelper.bind(indicator, viewPager);


    }

    @OnClick({
            R.id.iv_expandable,
            R.id.tv_section,
            R.id.scroll_view
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_expandable:

                navigator.notifyDataSetChanged();    // must call firstly
                fragmentAdapter.notifyDataSetChanged();

                isExpanded = !isExpanded;
                iv_expandable.setImageResource(isExpanded ? R.drawable.ic_expand_close : R.drawable.ic_expand_open);
                //栏目切换的动画
                ObjectAnimator animator0 = ObjectAnimator.ofFloat(tv_section, "translationX", isExpanded ? 0 : tv_section.getWidth());
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(scrollView, "translationY", isExpanded ? 0 : -scrollView.getHeight());
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator0, animator1);
                animatorSet.setDuration(500);
                animatorSet.setInterpolator(new BounceInterpolator());
                animatorSet.start();

                //保存已选择&未选择的栏目列表
                selectedTitleString = parseListToStringByColons(selectedTitleList);
                unselectedTitleString = parseListToStringByColons(unSelectedTitleList);
                App.getSpUtils().putString(Constants.STRING_TITLE_SELECTED, selectedTitleString);
                App.getSpUtils().putString(Constants.STRING_TITLE_UNSELECTED, unselectedTitleString);
                break;

            case R.id.tv_section:
                break;
            case R.id.scroll_view:
                break;
        }
    }

    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        BaseViewHolder holder = (BaseViewHolder) viewHolder;
        CardView cardView = holder.getView(R.id.cardview);
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_accent));
    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder viewHolder, int pos, RecyclerView.ViewHolder viewHolder1, int to) {
        fragmentList.add(to, fragmentList.remove(pos));//拖拽移动fragment
    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        BaseViewHolder holder = (BaseViewHolder) viewHolder;
        CardView cardView = holder.getView(R.id.cardview);
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_primary));
    }

    @Override
    public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
        BaseViewHolder holder = (BaseViewHolder) viewHolder;
        CardView cardView = holder.getView(R.id.cardview);
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_error));
    }

    @Override
    public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

    }

    @Override
    public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
        unselectedAdapter.add(unselectedAdapter.getData().size(), selectedAdapter.getItem(pos));

        fragmentList.remove(pos);
        navigator.notifyDataSetChanged();    // must call firstly
        fragmentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
        if (!isCurrentlyActive) {
            BaseViewHolder holder = (BaseViewHolder) viewHolder;
            CardView cardView = holder.getView(R.id.cardview);
            cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_primary));
        }
    }

    /**
     * 以:进行间隔区分，String->List
     */
    private List<String> parseStringToListByColons(String result) {
        List<String> resultList = new ArrayList<>();
        String[] resultArray = result.split(":");
        Collections.addAll(resultList, resultArray);
        return resultList;
    }

    /**
     * 以:进行间隔区分，List->String
     */
    private String parseListToStringByColons(List<String> list) {
        String resultString = "";
        for (int i = 0; i < list.size(); i++) {
            resultString = resultString + list.get(i) + ":";
        }
        return resultString;
    }
}
