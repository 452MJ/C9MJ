package com.c9mj.platform.live.danmu.utils;


import com.c9mj.platform.live.danmu.client.DanmuClient;

/**
 * Created by 小萌神_0 on 2016/5/29.
 */
public class KeepGetMsgThread extends Thread {
    @Override
    public void run()
    {
        ////获取弹幕客户端
        DanmuClient danmuClient = DanmuClient.getClient();

        //判断客户端就绪状态
        while(danmuClient.getReady())
        {
            //获取服务器发送的弹幕信息
            danmuClient.getServerMsg();
        }
    }
}
