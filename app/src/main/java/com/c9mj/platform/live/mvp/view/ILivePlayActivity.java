package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailDouyuBean;

/**
 * author: LMJ
 * date: 2016/9/20
 */
public interface ILivePlayActivity extends IErrorView {
    void updateLiveDetail(LiveDetailBean detailBean);//更新直播详情
    void updateDouyuDetail(LiveDetailDouyuBean detailDouyuBean);//更新斗鱼弹幕聊天室详情
}
