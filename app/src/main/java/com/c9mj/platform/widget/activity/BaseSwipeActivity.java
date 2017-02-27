package com.c9mj.platform.widget.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.utils.ToastUtils;
import com.c9mj.platform.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * author: liminjie
 * date: 2017/2/27
 * desc: BaseSwipeActivity侧滑基类
 */

public class BaseSwipeActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //6.0权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions
                    .requestEach(
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(permission -> {
                        if (permission.granted) {
                            // `permission.name` is granted !
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            ToastUtils.showShortToast(getString(R.string.error_grant));
                        }

                    });
        }
        ;
    }
}
