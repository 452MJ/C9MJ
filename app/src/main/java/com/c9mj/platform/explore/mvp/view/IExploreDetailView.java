package com.c9mj.platform.explore.mvp.view;


import com.c9mj.platform.explore.mvp.model.bean.ExploreDetailBean;
import com.c9mj.platform.util.retrofit.exception.IErrorView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public interface IExploreDetailView extends IErrorView {
    void updateWebView(String html);

    void updateExploreDetail(ExploreDetailBean detailBean);

    void updateRelativeSys(List<ExploreDetailBean.RelativeSysBean> relative_sys);//相关新闻
}
