package com.c9mj.platform.live.danmu.utils;


import com.c9mj.platform.live.danmu.client.DanmuClient;

/**
 * author: LMJ
 * date: 2016/9/14
 */
public class KeepAliveThread extends Thread {
    @Override
    public void run()
    {
        //获取弹幕客户端
        DanmuClient danmuClient = DanmuClient.getClient();

        //判断客户端就绪状态
        while(danmuClient.getReady())
        {
            //发送心跳保持协议给服务器端
            danmuClient.keepAlive();
            try
            {
                //设置间隔45秒再发送心跳协议
                Thread.sleep(45000);        //keep live at least once per minute
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
