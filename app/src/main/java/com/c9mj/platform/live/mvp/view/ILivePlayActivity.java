package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.mvp.model.DanmuBean;
import com.c9mj.platform.live.mvp.model.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.LivePandaBean;
import com.c9mj.platform.util.retrofit.exception.IErrorView;

/**
 * author: LMJ
 * date: 2016/9/20
 */
public interface ILivePlayActivity extends IErrorView {
    void updateLiveDetail(LiveDetailBean detailBean);//更新直播详情

    void updateChatDetail(LivePandaBean detailPandaBean);//更新熊猫弹幕聊天室详情

    void receiveDanmu(DanmuBean danmuBean, boolean withBorder);//添加弹幕
}
