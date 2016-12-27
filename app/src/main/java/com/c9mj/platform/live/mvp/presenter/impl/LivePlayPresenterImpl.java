package com.c9mj.platform.live.mvp.presenter.impl;

import android.text.TextUtils;

import com.c9mj.platform.live.api.LiveAPI;
import com.c9mj.platform.live.mvp.model.DanmuBean;
import com.c9mj.platform.live.mvp.model.LiveDetailBean;
import com.c9mj.platform.live.mvp.model.LivePandaBean;
import com.c9mj.platform.live.mvp.presenter.ILivePlayPresenter;
import com.c9mj.platform.live.mvp.view.ILivePlayActivity;
import com.c9mj.platform.util.DanmuUtil;
import com.c9mj.platform.util.GsonHelper;
import com.c9mj.platform.util.retrofit.HttpSubscriber;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * author: LMJ
 * date: 2016/9/20
 */

public class LivePlayPresenterImpl implements ILivePlayPresenter {

    private final ILivePlayActivity view;
    /**********************
     * 以下是弹幕Socket连接相关
     *****************/
    private ArrayList<String> chatRoomList = new ArrayList<>();
    private String socketIP;
    private int socketPort;
    private boolean isConnectSuccess = false;
    //心跳包相关
    private boolean isAlreadySendHeart = false;  //是否已发送心跳包
    private boolean isHeartStop = false;     //用于外部控制心跳线程结束
    //弹幕接收相关
    private boolean isReceiveStop = false;     //用于外部控制接收线程结束
    private Socket socket = null;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;

    public LivePlayPresenterImpl(ILivePlayActivity view) {
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
                    .subscribe(new DefaultSubscriber<LivePandaBean>() {
                        @Override
                        public void onComplete() {

                        }

                        @Override
                        public void onError(Throwable e) {
//                            view.showError("弹幕服务器接口已过期，请刷新直播列表！");
                            view.showError(e.getMessage());
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
        } else {
            view.showError("直播平台：" + live_type + "！不是熊猫TV的弹幕池！");
        }
    }

    @Override
    public void connectToChatRoom(String roomid, final LivePandaBean pandaBean) {

        chatRoomList = pandaBean.getData().getChat_addr_list();
        if (chatRoomList == null || chatRoomList.size() == 0) {
            view.showError("无法连接至弹幕服务器！");
            return;
        }

        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                //接收响应数据
                byte readData[] = new byte[6];

                socketIP = chatRoomList.get(0).split(":")[0];
                socketPort = Integer.parseInt(chatRoomList.get(0).split(":")[1]);
                socket = new Socket(socketIP, socketPort);

                bis = new BufferedInputStream(socket.getInputStream());//得到Socket输入流
                bos = new BufferedOutputStream(socket.getOutputStream());//得到Socket输出流

                //发送建立连接请求
                bos.write(DanmuUtil.getConnectData(pandaBean.getData()));
                bos.flush();
                bis.read(readData);
                int isLength = bis.read();

                if (isLength >= 6) {
                    if (!(readData[0] == DanmuUtil.RESPONSE[0] &&
                            readData[1] == DanmuUtil.RESPONSE[1] &&
                            readData[2] == DanmuUtil.RESPONSE[2] &&
                            readData[3] == DanmuUtil.RESPONSE[3]))
                        isConnectSuccess = false;
                    else {
                        isConnectSuccess = true;
                        e.onNext("connect success!");
                    }
                } else {
                    isConnectSuccess = false;
                }

            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<String>() {
                    @Override
                    public void onNext(String result) {
                        //连接成功
                        if (isConnectSuccess) {
                            runHeartDanmuOnRxJava();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void closeConnection() {

        if (socket != null &&
                bis != null &&
                bos != null &&
                socket.isConnected()) {
            try {
                isHeartStop = true;//停止心跳线程
                isReceiveStop = true;//停止弹幕消息接收
                bis.close();
                bos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 1.心跳包
     * 2.接收弹幕
     */
    private void runHeartDanmuOnRxJava() {
        //心跳包

//        Flowable.create(new FlowableOnSubscribe<String>() {
//            @Override
//            public void subscribe(FlowableEmitter<String> e) throws Exception {
//                int autoConnectedTime = 0;
//                int maxConnectedTime = 5;//自动断线重连次数
//                while (!isHeartStop){
//                    bos.write(DanmuUtil.getHeartData());//发送心跳包
//                    bos.flush();
//                    if (isAlreadySendHeart == true){
//                        connectToChatRoom(roomid, pandaBean); //连接断开，自动重新连接
//                        autoConnectedTime++;
//                        if (autoConnectedTime > maxConnectedTime){//超过最大重连次数
//                            autoConnectedTime = 0;
//                            e.onNext("无法连接至弹幕服务器！");
//                        }else {
//                            Thread.sleep(3 * 1000);//3s后再次尝试发送心跳包
//                            continue;
//                        }
//                    }
//                    isAlreadySendHeart = true;//修改标志已发送心跳包
//                    autoConnectedTime = 0;//断线重连计数置零
//
//                    //等到300s再次发送心跳包
//                    Thread.sleep(300 * 1000);
//                }
////                e.onNext("断开弹幕服务器连接！");
//            }
//        }, BackpressureStrategy.DROP)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultSubscriber<String>() {
//
//                    @Override
//                    public void onComplete() {
//                        closeConnection();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        view.showError(e.getMessage());
//                    }
//
//                    @Override
//                    public void onNext(String result) {
//                    }
//                });

        //接收弹幕
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                byte[] messege = new byte[1024];

                while (!isReceiveStop) {
                    //读取服务器返回信息，并获取返回信息的整体字节长度
                    int recvLen = bis.read(messege, 0, messege.length);
                    //根据实际获取的字节数初始化返回信息内容长度
                    byte[] realBuf = new byte[recvLen];
                    //按照实际获取的字节长度读取返回信息
                    System.arraycopy(messege, 0, realBuf, 0, recvLen);

                    if (recvLen >= 4) {//成功接收到数据
                        //分析帧头
                        if (realBuf[0] == DanmuUtil.RECEIVE_MSG[0] &&//1.弹幕
                                realBuf[1] == DanmuUtil.RECEIVE_MSG[1] &&
                                realBuf[2] == DanmuUtil.RECEIVE_MSG[2] &&
                                realBuf[3] == DanmuUtil.RECEIVE_MSG[3]) {

                            //{"type":"1","time":1477356608,"data":{"from":{"__plat":"android","identity":"30","level":"4","msgcolor":"","nickName":"看了还说了","rid":"45560306","sp_identity":"0","userName":""},"to":{"toroom":"15161"},"content":"我去"}}
                            String content = new String(realBuf, "UTF-8");

                            //第一条弹幕
                            int danmuFromIndex = content.indexOf("{\"type");
                            int danmuToIndex = content.indexOf("}}");

                            //第二条弹幕（可有）
                            int danmuFromIndex_2 = content.lastIndexOf("{\"type");
                            int danmuToIndex_2 = content.lastIndexOf("}}");

                            String danmu;//存放弹幕

                            danmu = content.substring(danmuFromIndex, danmuToIndex + 2);
                            if (TextUtils.isEmpty(danmu)) {//为空不发射事件
                                continue;
                            }
                            e.onNext(danmu);

                            //如果存在第二条弹幕
                            if (!(danmuFromIndex == danmuFromIndex_2 &&
                                    danmuToIndex == danmuToIndex_2)) {
                                danmu = content.substring(danmuFromIndex_2, danmuToIndex_2 + 2);
                                if (TextUtils.isEmpty(danmu)) {
                                    continue;
                                }
                                e.onNext(danmu);
                            }


                        } else if (messege[0] == DanmuUtil.HEART_BEAT_RESPONSE[0] &&//2.心跳包
                                messege[1] == DanmuUtil.HEART_BEAT_RESPONSE[1] &&
                                messege[2] == DanmuUtil.HEART_BEAT_RESPONSE[2] &&
                                messege[3] == DanmuUtil.HEART_BEAT_RESPONSE[3]) {
                            //接收到响应，重置心跳包发送标志位
                            isAlreadySendHeart = false;
                        }
                    }
                }
            }
        }, BackpressureStrategy.DROP)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<String>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(String result) {
                        if (isReceiveStop) {
                            if (!isDisposed()) {
                                dispose();
                                return;
                            }
                        }
                        parseDanmu(result);
                    }
                });

    }

    /**
     * 解析所接收弹幕的Json
     *
     * @param danmu
     */
    private void parseDanmu(final String danmu) {

        Flowable.create(new FlowableOnSubscribe<DanmuBean>() {
            @Override
            public void subscribe(FlowableEmitter<DanmuBean> e) throws Exception {
                //解析弹幕Json
                int one = danmu.indexOf("1");
                if (danmu.substring(one, one + 1).equals("1")) {
                    DanmuBean danmuBean = (DanmuBean) GsonHelper.parseJson(danmu, DanmuBean.class);
                    e.onNext(danmuBean);
                }
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultSubscriber<DanmuBean>() {
                    @Override
                    public void onComplete() {
                    }

                    @Override
                    public void onError(Throwable e) {
//                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(DanmuBean bean) {
                        view.receiveDanmu(bean, false);
                    }
                });
    }

}
