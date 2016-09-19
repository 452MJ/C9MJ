package com.c9mj.platform.user.ui;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.c9mj.platform.R;
import com.c9mj.platform.util.PhotoUtil;
import com.c9mj.platform.util.SpHelper;
import com.c9mj.platform.util.ToastUtil;
import com.c9mj.platform.widget.fragment.LazyFragment;

import org.joda.time.DateTime;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class UserFragment extends LazyFragment {

    private static final int REQUEST_CAMERA = 100;
    private static final int REQUEST_GALLERY = REQUEST_CAMERA + 1;

    private String savePath;//调用系统Camera相片保存的路径

    @BindView(R.id.user_iv_appbar)
    ImageView iv_appbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_btn_photo)
    FloatingActionButton btn_photo;

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

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle(getString(R.string.title_user));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        String filePath = SpHelper.with(this.getContext()).getString(SpHelper.STRING_USER);//查询保存的背景图片路径
        if (!TextUtils.isEmpty(filePath)) {
            Glide.with(this).load(filePath).into(iv_appbar);
        } else {
            Glide.with(this).load(R.drawable.background_default).into(iv_appbar);//加载默认背景
        }
    }

    @OnClick({R.id.user_btn_photo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_btn_photo: {
                final Context context = view.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Tips:")
                        .setMessage("如何获取图片？")
                        .setPositiveButton(getString(R.string.user_gallery), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                PhotoUtil.startGallery(UserFragment.this, REQUEST_GALLERY);
                            }
                        })
                        .setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(getString(R.string.user_carema), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                String fileName = "C9MJ_" + new DateTime(System.currentTimeMillis()).toString("yyyyMMddHHmmss") + ".png";
                                savePath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + fileName;
                                PhotoUtil.startTakePhoto(UserFragment.this, REQUEST_CAMERA, fileName);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.color_primary));
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.color_primary));
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(context.getResources().getColor(R.color.color_secondary_text));
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CAMERA: {// 调用相机拍照
                if (resultCode == Activity.RESULT_OK) {
                    //如果调用相机时指定了savePath的Uri，则不会返回data，data == null
                    //我们可以直接从之前设置savePath得到相片File
                    File temp = new File(savePath);
                    PhotoUtil.updateGallery(this.getContext(), savePath);
                    String filePath = temp.getAbsolutePath();
                    Glide.with(this).load(filePath).into(iv_appbar);
                    SpHelper.with(this.getContext()).setString(SpHelper.STRING_USER, filePath);//保存图片路径
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    ToastUtil.show(getString(R.string.user_carema_cancel));
                } else {
                    ToastUtil.show( getString(R.string.error_unknown));
                }
            }
            break;
            case REQUEST_GALLERY: {// 相册获取
                if (resultCode == Activity.RESULT_OK) {
                    String filePath = PhotoUtil.getRealPathFromURI(this.getContext(), data.getData());
                    Glide.with(this).load(filePath).into(iv_appbar);
                    SpHelper.with(this.getContext()).setString(SpHelper.STRING_USER, filePath);//保存图片路径
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    ToastUtil.show( getString(R.string.user_carema_cancel));
                } else {
                    ToastUtil.show(getString(R.string.error_unknown));
                }
            }
            break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
