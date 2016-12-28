package com.c9mj.platform.gallery.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.gallery.adapter.GalleryAdapter;
import com.c9mj.platform.gallery.mvp.presenter.impl.GalleryPresenterImpl;
import com.c9mj.platform.gallery.mvp.view.IGalleryView;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.widget.activity.BaseSwipeActivity;
import com.c9mj.platform.widget.inicator.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GalleryActivity extends BaseSwipeActivity implements IGalleryView {

    public static final String IMG_LIST = "img_list";
    private final List<View> viewList = new ArrayList<>();
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    private Context context;
    private GalleryPresenterImpl presenter;
    private GalleryAdapter adapter;
    private List<String> imgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        getWindow().setBackgroundDrawable(null);

        context = this;

        imgList = getIntent().getStringArrayListExtra(IMG_LIST);

        initView();
    }


    private void initView() {
        //初始化MVP
        presenter = new GalleryPresenterImpl(this);

        //设置TitleBar

        //设置RefreshLayout

        //设置RecyclerView

        //设置其他View
        //ViewPager
        for (String url : imgList) {
            PhotoView photoView = new PhotoView(context);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(lp);
            Glide.with(context).load(url)
                    .fitCenter()
                    .thumbnail(0.1f)
                    .into(photoView);
            viewList.add(photoView);
        }

        adapter = new GalleryAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);

        //MagicIndicator
        ScaleCircleNavigator navigator = new ScaleCircleNavigator(context);
        navigator.setFollowTouch(true);
        navigator.setCircleCount(viewList.size());
        navigator.setNormalCircleColor(Color.parseColor("#33ffffff"));
        navigator.setSelectedCircleColor(Color.parseColor("#ffffff"));
        magicIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }


    @Override
    public void showError(String message) {
        ToastUtil.show(message);
    }

    @OnClick({R.id.titlebar_iv_toggle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_toggle:
                finish();
                break;
        }
    }

}
