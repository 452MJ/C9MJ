package com.c9mj.platform.explore.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.explore.mvp.model.bean.ExploreDetailBean;
import com.c9mj.platform.explore.mvp.presenter.impl.ExploreDetailPresenterImpl;
import com.c9mj.platform.explore.mvp.view.IExploreDetailView;
import com.c9mj.platform.gallery.ui.GalleryActivity;
import com.c9mj.platform.widget.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ExploreDetailFragment extends BaseFragment implements IExploreDetailView {

    private static final String DOC_ID = "doc_id";
    private static final String TITLE = "title";
    private static final String IMG = "img";
    private final JsInterface jsInterface = new JsInterface();
    @BindView(R.id.iv_appbar)
    ImageView iv_appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView tv_title;
    @BindView(R.id.cardview_webview)
    CardView cv_webview;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.iv_img_0)
    ImageView iv_img_0;
    @BindView(R.id.tv_title_0)
    TextView tv_title_0;
    @BindView(R.id.tv_source_0)
    TextView tv_source_0;
    @BindView(R.id.tv_time_0)
    TextView tv_time_0;
    @BindView(R.id.iv_img_1)
    ImageView iv_img_1;
    @BindView(R.id.tv_title_1)
    TextView tv_title_1;
    @BindView(R.id.tv_source_1)
    TextView tv_source_1;
    @BindView(R.id.tv_time_1)
    TextView tv_time_1;
    @BindView(R.id.iv_img_2)
    ImageView iv_img_2;
    @BindView(R.id.tv_title_2)
    TextView tv_title_2;
    @BindView(R.id.tv_source_2)
    TextView tv_source_2;
    @BindView(R.id.tv_time_2)
    TextView tv_time_2;
    @BindView(R.id.cardview_relative)
    CardView cv_relative;
    @BindView(R.id.layout_scroller)
    NestedScrollView layout_scroller;
    private Context context;
    private String doc_id;
    private String title;
    private String img;
    private ExploreDetailPresenterImpl presenter;
    private ExploreDetailBean detailBean;
    private List<ExploreDetailBean.RelativeSysBean> relativeSysList = new ArrayList<>();

    public static ExploreDetailFragment newInstance(String doc_id, String title, String img) {
        ExploreDetailFragment fragment = new ExploreDetailFragment();
        Bundle args = new Bundle();
        args.putString(DOC_ID, doc_id);
        args.putString(TITLE, title);
        args.putString(IMG, img);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_detail, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        doc_id = getArguments().getString(DOC_ID);
        title = getArguments().getString(TITLE);
        img = getArguments().getString(IMG);

        initView();

        presenter.getExploreDetail(doc_id);
        return attachToSwipeBack(view);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        //初始化MVP
        presenter = new ExploreDetailPresenterImpl(this);

        //设置ToolBar
        tv_title.setText(title);
        tv_title.setSelected(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(null);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow_normal);
        toolbar.setNavigationOnClickListener(view -> pop());
        Glide.with(this).load(img).into(iv_appbar);

        //设置RefreshLayout

        //设置RecyclerView

        //设置其他View
        // 设置android下允许执行js的脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(jsInterface, "jsObj");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 80) {
                    layout_scroller.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void updateWebView(String html) {
//        webView.loadData(html, "text/html", null);
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    @Override
    public void updateExploreDetail(ExploreDetailBean detailBean) {
        this.detailBean = detailBean;
    }

    @Override
    public void updateRelativeSys(List<ExploreDetailBean.RelativeSysBean> relative_sys) {

        if (relative_sys.size() != 3) {
            return;
        }

        relativeSysList = relative_sys;

        cv_relative.setVisibility(View.VISIBLE);

        tv_title_0.setText(relative_sys.get(0).getTitle());
        tv_source_0.setText(relative_sys.get(0).getSource());
        tv_time_0.setText(relative_sys.get(0).getPtime());
        Glide.with(context).load(relative_sys.get(0).getImgsrc())
                .centerCrop()
                .crossFade()
                .into(iv_img_0);

        tv_title_1.setText(relative_sys.get(1).getTitle());
        tv_source_1.setText(relative_sys.get(1).getSource());
        tv_time_1.setText(relative_sys.get(1).getPtime());
        Glide.with(context).load(relative_sys.get(1).getImgsrc())
                .centerCrop()
                .crossFade()
                .into(iv_img_1);

        tv_title_2.setText(relative_sys.get(2).getTitle());
        tv_source_2.setText(relative_sys.get(2).getSource());
        tv_time_2.setText(relative_sys.get(2).getPtime());
        Glide.with(context).load(relative_sys.get(2).getImgsrc())
                .centerCrop()
                .crossFade()
                .into(iv_img_2);
    }

    @Override
    public void showError(String message) {
        ToastUtils.showShortToast(message);
    }


    @OnClick({R.id.layout_relative_0, R.id.layout_relative_1, R.id.layout_relative_2})
    public void onClick(View view) {
        ExploreDetailBean.RelativeSysBean relativeSysBean;
        switch (view.getId()) {
            case R.id.layout_relative_0:
                relativeSysBean = relativeSysList.get(0);
                start(ExploreDetailFragment.newInstance(
                        relativeSysBean.getDocID(),
                        relativeSysBean.getTitle(),
                        relativeSysBean.getImgsrc()));
                break;
            case R.id.layout_relative_1:
                relativeSysBean = relativeSysList.get(1);
                start(ExploreDetailFragment.newInstance(
                        relativeSysBean.getDocID(),
                        relativeSysBean.getTitle(),
                        relativeSysBean.getImgsrc()));
                break;
            case R.id.layout_relative_2:
                relativeSysBean = relativeSysList.get(2);
                start(ExploreDetailFragment.newInstance(
                        relativeSysBean.getDocID(),
                        relativeSysBean.getTitle(),
                        relativeSysBean.getImgsrc()));
                break;
        }
    }


    private class JsInterface {
        @JavascriptInterface
        public void startGallaryOnAndroid(int index) {
            Flowable.just(index)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(index1 -> {
                        Intent intent = new Intent(getActivity(), GalleryActivity.class);
                        ArrayList<String> imgList = new ArrayList<>();
                        for (ExploreDetailBean.ImgBean imgBean : detailBean.getImg()) {
                            imgList.add(imgBean.getSrc());
                        }
                        intent.putStringArrayListExtra(GalleryActivity.IMG_LIST, imgList);
                        intent.putExtra(GalleryActivity.INDEX, index1);
                        startActivity(intent);
                    });
        }
    }
}
