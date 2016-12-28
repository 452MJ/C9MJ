package com.c9mj.platform.widget.activity;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;

import com.c9mj.platform.R;
import com.c9mj.platform.util.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Administrator on 2016/11/2.
 */

public class BaseActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //6.0权限申请
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions rxPermissions = RxPermissions.getInstance(this);
            rxPermissions
                    .request(
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean granted) throws Exception {
                            if (granted) {
                                //所有权限被允许
                            } else {
                                //至少一个权限被拒绝
                                ToastUtil.show(getString(R.string.error_grant));
                            }
                        }

                    });
        }
    }
}
