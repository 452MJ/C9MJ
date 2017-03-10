package com.c9mj.platform.explore.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.c9mj.platform.R;
import com.c9mj.platform.explore.adapter.ExploreListAdapter;
import com.c9mj.platform.explore.api.ExploreAPI;
import com.c9mj.platform.explore.mvp.model.bean.ExploreListItemBean;
import com.c9mj.platform.explore.mvp.presenter.impl.ExploreListPresenterImpl;
import com.c9mj.platform.explore.mvp.view.IExploreListFragment;
import com.c9mj.platform.gallery.ui.GalleryActivity;
import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.main.ui.MainFragment;
import com.c9mj.platform.widget.recyclerview.animation.CustomAnimation;
import com.c9mj.platform.widget.fragment.BaseFragment;
import com.c9mj.platform.widget.recyclerview.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author: LMJ
 * date: 2016/9/19
 * 直播列表
 */
public class ExploreListFragment extends BaseFragment implements IExploreListFragment,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private static final String EXPLORE_TYPE_ID = "explore_type_id";
    private final List<ExploreListItemBean> exploreList = new ArrayList<>();
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private String explore_type_id;
    private int offset = 0;//用于记录分页偏移量
    private Context context;
    private ExploreListPresenterImpl presenter;
    private ExploreListAdapter adapter;

    public static ExploreListFragment newInstance() {
        return newInstance("");
    }

    public static ExploreListFragment newInstance(String explore_type_id) {
        ExploreListFragment fragment = new ExploreListFragment();
        Bundle args = new Bundle();
        args.putString(EXPLORE_TYPE_ID, explore_type_id);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_explore_list, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        Log.d(TAG, "onCreateView: ");

        explore_type_id = getArguments().getString(EXPLORE_TYPE_ID);//得到传入的explore_type_id

        initView();

        return view;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        _mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        if (savedInstanceState == null) {
            refreshLayout.setProgressViewOffset(false, 0, 30);// 这句话是为了，第一次进入页面初始化数据的时候显示加载进度条
            refreshLayout.setRefreshing(true);
            //根据game_type分类请求直播数据
            presenter.getExploreList(explore_type_id, offset, LiveAPI.LIMIT);
        }
    }

    private void initView() {
        //初始化MVP
        presenter = new ExploreListPresenterImpl(this);

        //设置RefreshLayout
        refreshLayout.setColorSchemeResources(R.color.color_primary);
        refreshLayout.setOnRefreshListener(this);

        //设置RecyclerView
        adapter = new ExploreListAdapter(exploreList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.openLoadAnimation(new CustomAnimation());
        adapter.isFirstOnly(true);
        adapter.setAutoLoadMoreSize(ExploreAPI.LIMIT);//加载更多的触发条件
        adapter.setOnLoadMoreListener(this);//加载更多回调监听
        adapter.setLoadMoreView(new CustomLoadMoreView());

        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, (ViewGroup) recyclerView.getParent(), false);
        TextView tv_empty = (TextView) emptyView.findViewById(R.id.tv_empty);
        tv_empty.setText(getString(R.string.explore_empty));
        adapter.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ExploreListItemBean exploreItemBean = adapter.getData().get(i);
                switch (exploreItemBean.getItemType()) {
                    case ExploreListItemBean.NORMAL:
                        SupportFragment exploreFragment = (ExploreFragment) getParentFragment();
                        SupportFragment mainFragment = (MainFragment) exploreFragment.getParentFragment();
                        mainFragment.start(ExploreDetailFragment.newInstance(
                                exploreItemBean.getDocid(),
                                exploreItemBean.getTitle(),
                                exploreItemBean.getImgsrc()));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ExploreListItemBean exploreItemBean = adapter.getData().get(i);
                switch (view.getId()) {
                    case R.id.viewpager:

                        ViewPager viewPager = (ViewPager) view;
                        int pos = viewPager.getCurrentItem();//第几个广告
                        if (pos == 0) {
                            String skipType = exploreItemBean.getSkipType();//跳转类型
                            if (TextUtils.equals(skipType, getString(R.string.explore_tag_doc))) {//doc-->ExploreDetailFragment
                                SupportFragment exploreFragment = (ExploreFragment) getParentFragment();
                                SupportFragment mainFragment = (MainFragment) exploreFragment.getParentFragment();
                                mainFragment.start(ExploreDetailFragment.newInstance(
                                        exploreItemBean.getDocid(),
                                        exploreItemBean.getTitle(),
                                        exploreItemBean.getImgsrc()));
                            } else if (TextUtils.equals(skipType, getString(R.string.explore_tag_photoset))) {//photoset-->GalleryActivity
                                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                                intent.putExtra(GalleryActivity.PHOTO_SET, exploreItemBean.getPhotosetID());
                                startActivity(intent);
                            }
                        } else {
                            ExploreListItemBean.AdsBean adsBean = exploreItemBean.getAds().get(pos - 1);
                            String skipType = adsBean.getTag();//跳转类型
                            if (TextUtils.equals(skipType, getString(R.string.explore_tag_doc))) {//doc-->ExploreDetailFragment
                                SupportFragment exploreFragment = (ExploreFragment) getParentFragment();
                                SupportFragment mainFragment = (MainFragment) exploreFragment.getParentFragment();
                                mainFragment.start(ExploreDetailFragment.newInstance(
                                        adsBean.getUrl(),
                                        adsBean.getTitle(),
                                        adsBean.getImgsrc()));
                            } else if (TextUtils.equals(skipType, getString(R.string.explore_tag_photoset))) {//photoset-->GalleryActivity
                                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                                intent.putExtra(GalleryActivity.PHOTO_SET, exploreItemBean.getPhotosetID());
                                startActivity(intent);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }
        });

        /***设置其他View***/
    }

    @Override
    public void updateRecyclerView(List<ExploreListItemBean> list) {
        refreshLayout.setRefreshing(false);
        adapter.addData(list);//在roomBeanList的尾部添加
        offset = adapter.getData().size();
        if (list.size() < LiveAPI.LIMIT) {//分页数据size比每页数据的limit小，说明已全部加载数据
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }

    @Override
    public void showError(String message) {
        refreshLayout.setRefreshing(false);
        adapter.loadMoreFail();//在加载失败的时候调用showLoadMoreFailedView()就能显示加载失败的footer了，点击footer会重新加载
        ToastUtils.showShortToast(message);
    }

    @Override
    public void onRefresh() {
        offset = 0;//重置偏移量
        exploreList.clear();//清空原数据
        adapter.setNewData(exploreList);
        adapter.removeAllFooterView();
        refreshLayout.setRefreshing(true);
        //根据explore_type_id请求更多新闻数据
        presenter.getExploreList(explore_type_id, offset, ExploreAPI.LIMIT);
    }

    @Override
    public void onLoadMoreRequested() {
        //根据explore_type_id请求更多新闻数据
        presenter.getExploreList(explore_type_id, offset, ExploreAPI.LIMIT);
    }
}
