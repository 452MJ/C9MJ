package com.c9mj.platform.user.ui;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.utils.ToastUtils;
import com.bumptech.glide.Glide;
import com.c9mj.platform.App;
import com.c9mj.platform.R;
import com.c9mj.platform.util.global.Constants;
import com.c9mj.platform.widget.fragment.BaseFragment;
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;
import com.yalantis.ucrop.UCrop;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class UserFragment extends BaseFragment {

    @BindView(R.id.iv_appbar)
    ImageView iv_appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_photo)
    FloatingActionButton btn_photo;
    private String savePath;//调用系统Camera相片保存的路径

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        //初始化MVP

        //设置RefreshLayout

        //设置RecyclerView

        /***设置其他View***/
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initToolBar();
    }


    private void initToolBar() {
        toolbar.setTitle(getString(R.string.user));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        String filePath = App.getSpUtils().getString(Constants.STRING_USER);//查询保存的背景图片路径
        if (!TextUtils.isEmpty(filePath)) {
            Glide.with(this).load(filePath).into(iv_appbar);
        } else {
            Glide.with(this).load(R.drawable.background_default).into(iv_appbar);//加载默认背景
        }
    }

    @OnClick({R.id.toolbar, R.id.btn_photo})
    public void onClick(View view) {
        UCrop.Options options = new UCrop.Options();
        int color = ContextCompat.getColor(view.getContext(), R.color.color_primary);
        options.setToolbarColor(color);
        options.setStatusBarColor(ContextCompat.getColor(view.getContext(), R.color.color_primary_dark));
        options.setActiveWidgetColor(color);
        switch (view.getId()) {
            case R.id.toolbar:
                break;
            case R.id.btn_photo: {
                final Context context = view.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Tips:")
                        .setMessage("如何获取图片？")
                        .setPositiveButton(getString(R.string.user_gallery), (dialog, which) -> {
                            dialog.dismiss();
                            RxPaparazzo.takeImage(UserFragment.this)
                                    .crop(options)
                                    .usingGallery()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(response -> {
                                        if (response.resultCode() == Activity.RESULT_OK) {
                                            String filePath = response.data();
                                            Glide.with(this).load(filePath).into(iv_appbar);
                                            App.getSpUtils().putString(Constants.STRING_USER, filePath);//保存图片路径
                                        } else if (response.resultCode() == Activity.RESULT_CANCELED) {
                                            ToastUtils.showShortToast(getString(R.string.user_carema_cancel));
                                        } else {
                                            ToastUtils.showShortToast(getString(R.string.error_unknown));
                                        }
                                    });
                        })
                        .setNeutralButton(getString(R.string.cancel), (dialog, which) -> dialog.dismiss())
                        .setNegativeButton(getString(R.string.user_carema), (dialog, which) -> {
                            dialog.dismiss();
                            RxPaparazzo.takeImage(UserFragment.this)
                                    .crop(options)
                                    .usingCamera()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(response -> {
                                        if (response.resultCode() == Activity.RESULT_OK) {
                                            String filePath = response.data();
                                            Glide.with(this).load(filePath).into(iv_appbar);
                                            App.getSpUtils().putString(Constants.STRING_USER, filePath);//保存图片路径
                                        } else if (response.resultCode() == Activity.RESULT_CANCELED) {
                                            ToastUtils.showShortToast(getString(R.string.user_carema_cancel));
                                        } else {
                                            ToastUtils.showShortToast(getString(R.string.error_unknown));
                                        }
                                    });
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.color_primary));
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.color_primary));
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(context, R.color.color_secondary_text));
                break;
            }
        }
    }
}
