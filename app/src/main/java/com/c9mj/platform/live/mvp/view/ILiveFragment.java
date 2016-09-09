package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.bean.LiveIndicatorBean;

import java.util.List;

/**
 * author: LMJ
 * date: 2016/9/9
 */
public interface ILiveFragment {
    void updateIndicator(List<LiveIndicatorBean> columnBeanList);//更新分类的Indicator
    void showError(String message);//异常错误
}
