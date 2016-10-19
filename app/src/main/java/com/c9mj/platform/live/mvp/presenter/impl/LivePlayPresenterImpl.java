package com.c9mj.platform.live.mvp.presenter.impl;

import android.content.Context;

import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.bean.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.bean.LivePandaBean;
import com.c9mj.platform.live.mvp.presenter.ILivePlayPresenter;
import com.c9mj.platform.live.mvp.view.ILivePlayActivity;
import com.c9mj.platform.util.DanmuUtil;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author: LMJ
 * date: 2016/9/20
 */

public class LivePlayPresenterImpl implements ILivePlayPresenter {

    private Context context;
    private ILivePlayActivity view;

    public LivePlayPresenterImpl(Context context, ILivePlayActivity view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getLiveDetail(String live_type, String live_id, String game_type) {
        RetrofitHelper.getLiveHelper().create(LiveAPI.class)
                .getLiveDetail(live_type, live_id, game_type)
                .compose(RetrofitHelper.<LiveDetailBean>handleLiveResult())
                .subscribe(new HttpSubscriber<LiveDetailBean>() {
                    @Override
                    public void _onNext(LiveDetailBean liveDetailBean) {
                        view.updateLiveDetail(liveDetailBean);
                    }

                    @Override
                    public void _onError(String message) {
                        view.showError(message);
                    }
                });
    }

    @Override
    public void getDanmuDetail(String roomid, String live_type) {
        if (live_type.equals("panda")) {
            RetrofitHelper.getPandaHelper().create(LiveAPI.class)
                    .getPandaChatroom(roomid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LivePandaBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showError("弹幕服务器接口已过期，请刷新直播列表！");
                        }

                        @Override
                        public void onNext(LivePandaBean detailPandaBean) {
                            if (detailPandaBean.getErrno() == 0) {
                                view.updateChatDetail(detailPandaBean);
                            } else {
                                view.showError(detailPandaBean.getErrmsg());
                            }
                        }
                    });
        }else {
            view.showError("直播平台：" + live_type +"！不是熊猫TV的弹幕池！");
        }
    }


    /**
     * 以下是弹幕Socket连接相关
     */

    private List<String> chatRoomList = new ArrayList(){};
    private String socketIP;
    private int socketPort;

    //建立连接相关
    private String roomid;
    private LivePandaBean pandaBean;
    private boolean isConnectSuccess = false;

    //心跳包线程相关
    private HeartRunnable heart;
    private boolean isAlreadySendHeart = false;  //是否已发送心跳包
    private boolean isHeartStop = false;     //用于外部控制线程结束

    private Socket socket = null;
    private InputStream inputStream;
    private OutputStream outputStream;

    @Override
    public void connectToChatRoom(String roomid, LivePandaBean pandaBean) {

        this.roomid = roomid;
        this.pandaBean = pandaBean;

        chatRoomList = pandaBean.getData().getChat_addr_list();
        if (chatRoomList == null || chatRoomList.size() == 0){
            view.showError("无法连接至弹幕服务器！");
            return;
        }
        try {
            socketIP = chatRoomList.get(0).split(":")[0];
            socketPort = Integer.parseInt(chatRoomList.get(0).split(":")[1]);
            socket = new Socket(socketIP, socketPort);

            inputStream = socket.getInputStream();//得到Socket输入流
            outputStream = socket.getOutputStream();//得到Socket输出流

            //发送建立连接请求
            outputStream.write(DanmuUtil.getConnectData(pandaBean.getData()));

            //接收响应数据
            byte readData[] = new byte[6];
            inputStream.read(readData);
            int isLength = inputStream.read(readData);
            if (isLength >= 6){
                if(!(readData[0] == DanmuUtil.RESPONSE[0] &&
                        readData[1] == DanmuUtil.RESPONSE[1] &&
                        readData[2] == DanmuUtil.RESPONSE[2] &&
                        readData[3] == DanmuUtil.RESPONSE[3]))
                    isConnectSuccess = false;
                else{
                    isConnectSuccess = true;
                    //消息主体，暂时用不到
				/*	short dataLength=(short) (readData[5]|(readData[4]<<8));
					byte[] data=new byte[dataLength];//数据
					is.read(data);//主体数据，appid+r的值，eg:id:845694055\nr:0  暂时用不到
				*/
                }
            }else {
                isConnectSuccess = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
       }
        //建立连接成功
        if (isConnectSuccess == true){
            heart = new HeartRunnable();
        }
    }

    @Override
    public void closeConnection() {

    }

    private class HeartRunnable implements Runnable {

        private int autoConnectedTime = 0;
        private int maxConnectedTime = 5;//自动断线重连次数

        @Override
        public void run() {
            while (isHeartStop == false){
                try {
                    outputStream.write(DanmuUtil.getHeartData());//发送心跳包
                    if (isAlreadySendHeart == true){
                        connectToChatRoom(roomid, pandaBean); //连接断开，自动重新连接
                        autoConnectedTime++;
                        if (autoConnectedTime > maxConnectedTime){//超过最大重连次数
                            view.showError("无法连接至弹幕服务器！");
                            autoConnectedTime = 0;
                            closeConnection();
                        }else {
                            Thread.sleep(3000);//三秒后再次尝试发送心跳包
                            continue;
                        }
                    }
                    isAlreadySendHeart = true;//修改标志已发送心跳包
                    autoConnectedTime = 0;//断线重连计数置零

                    //等到300s再次发送心跳包
                    Thread.sleep(300 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
