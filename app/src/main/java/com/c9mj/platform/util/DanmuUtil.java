package com.c9mj.platform.util;


import com.c9mj.platform.live.mvp.model.LivePandaBean;

/**
 * author: liminjie
 * date: 2017/2/27
 * desc: DanmuUtil弹幕数据包生成工具类
 */

public class DanmuUtil {

    public final static byte[] START_FLAG = {0x00, 0x06, 0x00, 0x02};  //连接弹幕服务器帧头
    public final static byte[] RESPONSE = {0x00, 0x06, 0x00, 0x06};  //连接弹幕服务器响应
    public final static byte[] KEEP_ALIVE = {0x00, 0x06, 0x00, 0x00};  //与弹幕服务器心跳心跳保持
    public final static byte[] RECEIVE_MSG = {0x00, 0x06, 0x00, 0x03}; //接收到弹幕消息的帧头
    public final static byte[] HEART_BEAT_RESPONSE = {0x00, 0x06, 0x00, 0x01};//心跳保持服务器返回的值
    public final static int IGNORE_BYTE_LENGTH = 16;//弹幕消息体忽略的字节数
    public final static int MAX_AUTO_CONNECT_TIME = 5;//自动断线重连次数

    /**
     * 建立连接请求数据包
     *
     * @param bean
     * @return
     */
    public static byte[] getConnectData(LivePandaBean.DataBean bean) {
        String contentMsg = "u:" + bean.getRid() + "" +
                "@" + bean.getAppid() + "" +
                "\nk:1" +
                "\nt:300" +
                "\nts:" + bean.getTs() + "" +
                "\nsign:" + bean.getSign() + "" +
                "\nauthtype:" + bean.getAuthType();
        byte content[] = contentMsg.getBytes();
        byte length[] = {(byte) (content.length >> 8), (byte) (content.length & 0xff)};
        byte sendMessage[] = new byte[START_FLAG.length + 2 + content.length];
        /**
         * 1.帧头
         * 2.content[]的数据长度
         * 3.content[]
         */
        System.arraycopy(START_FLAG, 0, sendMessage, 0, START_FLAG.length);
        System.arraycopy(length, 0, sendMessage, START_FLAG.length, length.length);
        System.arraycopy(content, 0, sendMessage, START_FLAG.length + length.length, content.length);
        return sendMessage;
    }

    public static byte[] getHeartData() {
        return KEEP_ALIVE;
    }

    public static int byte2Int(byte[] bytes) {
        int value = 0;
        byte temp;

        for (int i = 0; i < bytes.length; i++) {
            temp = bytes[i];
            value += (temp & 0xFF) << (8 * i);
        }
        return value;
    }

}
