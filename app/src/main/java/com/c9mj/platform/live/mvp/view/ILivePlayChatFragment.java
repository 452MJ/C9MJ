package com.c9mj.platform.live.mvp.view;

import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailPandaBean;
import com.c9mj.platform.util.retrofit.exception.IErrorView;

/**
 * author: LMJ
 * date: 2016/9/20
 */
public interface ILivePlayChatFragment extends IErrorView {
    void updateLiveDetail(LiveDetailBean detailBean);//更新直播详情
    void updateDouyuDetail(LiveDetailPandaBean detailPandaBean);//更新熊猫弹幕聊天室详情
}
