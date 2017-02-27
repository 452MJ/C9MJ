package com.c9mj.platform.gallery.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.gallery.adapter.GalleryAdapter;
import com.c9mj.platform.gallery.mvp.presenter.impl.GalleryPresenterImpl;
import com.c9mj.platform.gallery.mvp.view.IGalleryView;
import com.c9mj.platform.widget.activity.BaseSwipeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GalleryActivity extends BaseSwipeActivity implements IGalleryView, ViewPager.OnPageChangeListener {

    public static final String PHOTO_SET = "photoset";
    public static final String INDEX = "index";
    public static final String IMG_LIST = "img_list";

    private final List<View> viewList = new ArrayList<>();
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.tv_index)
    TextView tv_index;
    private Context context;
    private GalleryPresenterImpl presenter;
    private GalleryAdapter adapter;
    private String photoSetId;
    private List<String> imgList = new ArrayList<>();
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);

        context = this;

        photoSetId = getIntent().getStringExtra(PHOTO_SET);
        if (TextUtils.isEmpty(photoSetId)) {//photoSetId为空，直接取imgList
            imgList = getIntent().getStringArrayListExtra(IMG_LIST);
            index = getIntent().getIntExtra(INDEX, 0);
        }

        initView();

        if (TextUtils.isEmpty(photoSetId)) {//photoSetId为空，可以设置ViewPager
            initViewPager();
        } else {//photoSetId不为空，请求获取具体的photoSet
            presenter.getExploreSet(photoSetId);
        }
    }


    private void initView() {
        //初始化MVP
        presenter = new GalleryPresenterImpl(this);

        //设置TitleBar

        //设置RefreshLayout

        //设置RecyclerView

        //设置其他View

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        for (String url : imgList) {
            PhotoView photoView = new PhotoView(context);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(lp);
            Glide.with(context).load(url)
                    .fitCenter()
                    .thumbnail(0.1f)
                    .into(photoView);
            viewList.add(photoView);

            viewPager.setCurrentItem(index);
            tv_index.setText((index + 1) + "/" + imgList.size());
        }

        adapter = new GalleryAdapter(viewList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        tv_index.setText((position + 1) + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void updateViewPager(ArrayList<String> imgList) {
        this.imgList.addAll(imgList);
        initViewPager();
    }

    @Override
    public void showError(String message) {
        ToastUtils.showShortToast(message);
    }

    @OnClick({R.id.titlebar_iv_toggle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_iv_toggle:
                finish();
                break;
            default:
                break;
        }
    }
}
