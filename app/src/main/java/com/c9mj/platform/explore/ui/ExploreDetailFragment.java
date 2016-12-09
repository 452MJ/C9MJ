package com.c9mj.platform.explore.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.explore.mvp.model.bean.ExploreDetailBean;
import com.c9mj.platform.explore.mvp.presenter.impl.ExploreDetailPresenterImpl;
import com.c9mj.platform.explore.mvp.view.IExploreDetailView;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.widget.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.c9mj.platform.R.id.webview;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ExploreDetailFragment extends BaseFragment implements IExploreDetailView {

    private static final String DOC_ID = "key";

    Context context;
    String doc_id;

    ExploreDetailPresenterImpl presenter;

    List<ExploreDetailBean.RelativeSysBean> relativeSysList = new ArrayList<>();
    JsInterface jsInterface = new JsInterface();

    @BindView(R.id.cardview_webview)
    CardView cv_webview;
    @BindView(webview)
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
    ScrollView layout_scroller;
    @BindView(R.id.layout_progressbar)
    FrameLayout layout_progressbar;


    public static ExploreDetailFragment newInstance() {
        return newInstance("");
    }

    public static ExploreDetailFragment newInstance(String doc_id) {
        ExploreDetailFragment fragment = new ExploreDetailFragment();
        Bundle args = new Bundle();
        args.putString(DOC_ID, doc_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore_detail, container, false);
        ButterKnife.bind(this, view);

        context = view.getContext();

        initView();

        doc_id = getArguments().getString(DOC_ID);

        presenter.getExploreDetail(doc_id);
        return attachToSwipeBack(view);
    }


    private void initView() {
        //初始化MVP
        presenter = new ExploreDetailPresenterImpl(this);

        //设置RefreshLayout

        //设置RecyclerView

        //设置其他View
        // 设置android下容许执行js的脚本
        // 编码方式
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.addJavascriptInterface(jsInterface, "jsObj");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 80) {
                    layout_progressbar.setVisibility(View.GONE);
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
        ToastUtil.show(message);
    }


    @OnClick({R.id.iv_back, R.id.layout_relative_0, R.id.layout_relative_1, R.id.layout_relative_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.layout_relative_0:
                start(ExploreDetailFragment.newInstance(relativeSysList.get(0).getDocID()));
                break;
            case R.id.layout_relative_1:
                start(ExploreDetailFragment.newInstance(relativeSysList.get(1).getDocID()));
                break;
            case R.id.layout_relative_2:
                start(ExploreDetailFragment.newInstance(relativeSysList.get(2).getDocID()));
                break;
        }
    }

    class JsInterface{
        @JavascriptInterface
        public void startGallaryOnAndroid() {
            Flowable.just(0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            ToastUtil.show(integer + "");
                        }
                    });
//            start(ExploreDetailFragment.newInstance());
        }
    }
}
