package com.c9mj.platform.live.danmu.client;

import android.util.Log;

import com.c9mj.platform.live.danmu.msg.DanmuSocketPackage;
import com.c9mj.platform.live.danmu.msg.MsgView;
import com.c9mj.platform.live.danmu.utils.KeepAliveThread;
import com.c9mj.platform.live.danmu.utils.KeepGetMsgThread;
import com.c9mj.platform.util.retrofit.RetrofitHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

/**
 * author: LMJ
 * date: 2016/9/14
 * 后台管理弹幕服务器socket连接
 */
public class DanmuClient {
    private static final String TAG = DanmuClient.class.getSimpleName();
    private static DanmuClient client;

    //第三方弹幕协议服务器地址
    private static final String HOST = RetrofitHelper.BASE_PANDA_URL;

    //第三方弹幕协议服务器端口
    private static final int SOCKET_PORT = 8601;

    //设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 4096;

    //socket相关配置
    private Socket socket;
    private BufferedOutputStream outputStream;
    private BufferedInputStream inputStream;

    //获取弹幕线程及心跳线程运行和停止标记
    private boolean isReady = false;

    private DanmuClient(){}

    /**
     * 单例获取方法，客户端单例模式访问
     * @return
     */
    public static DanmuClient getClient(){
        if(null == client){
            synchronized (DanmuClient.class) {
                client = new DanmuClient();
            }
        }
        return client;
    }

    /****************分发传递弹幕接口**********************/
    public interface HandleDanmuMsgListener {
        void handleDanmuMsg(String content);
    }
    private HandleDanmuMsgListener danmuMsgListener = null;
    public void setHandleDanmuMsgListener(HandleDanmuMsgListener mHandleMsgListener) {
        this.danmuMsgListener = mHandleMsgListener;
    }
    private boolean isHandleMsgListenerNull() {
        return danmuMsgListener == null;
    }

    /**
     * 启动弹幕客户端
     * @param roomId
     * @param groupId
     */
    public void start(int roomId, int groupId) {
        init(roomId, groupId);

        KeepAliveThread keepAlive = new KeepAliveThread();
        keepAlive.start();

        KeepGetMsgThread keepGetMsg = new KeepGetMsgThread();
        keepGetMsg.start();
    }

    /**
     * 客户端初始化，连接弹幕服务器并登陆房间及弹幕池
     * @param roomId 房间ID
     * @param groupId 弹幕池分组ID
     */
    public void init(int roomId, int groupId){
        //连接弹幕服务器
        this.connectServer();
        //登陆指定房间
        this.loginRoom(roomId);
        //加入指定的弹幕池
        this.joinGroup(roomId, groupId);
        //设置客户端就绪标记为就绪状态
        isReady = true;
    }

    public void stop() {
        unInit();
    }

    public void unInit() {
        isReady = false;
    }

    /**
     * 获取弹幕客户端就绪标记
     * @return
     */
    public boolean getReady(){
        return isReady;
    }

    /**
     * 连接弹幕服务器
     */
    private void connectServer()
    {
        try
        {
            //获取弹幕服务器访问host
            String host = InetAddress.getByName(HOST).getHostAddress();
            //建立socket连接
            socket = new Socket(host, SOCKET_PORT);
            //设置socket输入及输出
            outputStream = new BufferedOutputStream(socket.getOutputStream());
            inputStream = new BufferedInputStream(socket.getInputStream());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        Log.d(TAG, "connectServer: Server Connect Successfully!");
    }

    /**
     * 登录指定房间
     * @param roomId
     */
    private void loginRoom(int roomId)
    {
        //获取弹幕服务器登陆请求数据包
        byte[] loginRequestData = DanmuSocketPackage.getLoginRequestData(roomId);


        try{
            //发送登陆请求数据包给弹幕服务器
            outputStream.write(loginRequestData, 0, loginRequestData.length);
            outputStream.flush();

            //初始化弹幕服务器返回值读取包大小
            byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
            //获取弹幕服务器返回值
            inputStream.read(recvByte, 0, recvByte.length);

            //解析服务器返回的登录信息
            if(DanmuSocketPackage.parseLoginRespond(recvByte)){
                Log.d(TAG, "loginRoom: Receive login response successfully!");
            } else {
                Log.d(TAG, "loginRoom: Receive login response failed!!");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加入弹幕分组池
     * @param roomId
     * @param groupId
     */
    private void joinGroup(int roomId, int groupId)
    {
        //获取弹幕服务器加弹幕池请求数据包
        byte[] joinGroupRequest = DanmuSocketPackage.getJoinGroupRequest(roomId, groupId);

        try{
            //想弹幕服务器发送加入弹幕池请求数据
            outputStream.write(joinGroupRequest, 0, joinGroupRequest.length);
            outputStream.flush();
            Log.d(TAG, "joinGroup: Send join group request successfully!");
        } catch(Exception e){
            e.printStackTrace();
            Log.e(TAG, "joinGroup: Send join group request failed!");
        }
    }

    /**
     * 服务器心跳连接
     */
    public void keepAlive()
    {
        //获取与弹幕服务器保持心跳的请求数据包
        byte[] keepAliveRequest = DanmuSocketPackage.getKeepAliveData((int)(System.currentTimeMillis() / 1000));

        try{
            //向弹幕服务器发送心跳请求数据包
            outputStream.write(keepAliveRequest, 0, keepAliveRequest.length);
            outputStream.flush();
            Log.d(TAG, "keepAlive: Send keep alive request successfully!");
        } catch(Exception e){
            e.printStackTrace();
            Log.e(TAG, "keepAlive: Send keep alive request failed!");
        }
    }

    /**
     * 获取服务器返回信息
     */
    public void getServerMsg(){
        //初始化获取弹幕服务器返回信息包大小
        byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
        //定义服务器返回信息的字符串
        String dataStr;
        try {
            //读取服务器返回信息，并获取返回信息的整体字节长度
            int recvLen = inputStream.read(recvByte, 0, recvByte.length);

            //根据实际获取的字节数初始化返回信息内容长度
            byte[] realBuf = new byte[recvLen];
            //按照实际获取的字节长度读取返回信息
            System.arraycopy(recvByte, 0, realBuf, 0, recvLen);
            //根据TCP协议获取返回信息中的字符串信息
            dataStr = new String(realBuf, 12, realBuf.length - 12);

//            //循环处理socekt黏包情况
//            while(dataStr.lastIndexOf("type@=") > 5){
//                //对黏包中最后一个数据包进行解析
//                MsgView msgView = new MsgView(TextUtils.substring(dataStr, 0, dataStr.lastIndexOf("type@=") - 1));
//                //分析该包的数据类型，以及根据需要进行业务操作
//                parseServerMsg(msgView.getMessageList());
//                //处理黏包中的剩余部分
//                dataStr = TextUtils.substring(dataStr, 0, dataStr.lastIndexOf("type@=") - 12);
//            }
            //对单一数据包进行解析
            MsgView msgView = new MsgView(dataStr);
            //分析该包的数据类型，以及根据需要进行业务操作
            parseServerMsg(msgView.getMessageList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析从服务器接受的协议，并根据需要订制业务需求
     * @param msg
     */
    private void parseServerMsg(Map<String, Object> msg){
        if(msg.get("type") != null){

            //服务器反馈错误信息
            if(msg.get("type").equals("error")){
                Log.d(TAG, "parseServerMsg: msg.toString()=" + msg.toString());
                //结束心跳和获取弹幕线程
                this.isReady = false;
            }

            /***@TODO 根据业务需求来处理获取到的所有弹幕及礼物信息***********/

            //判断消息类型
            if(msg.get("type").equals("chatmsg")){//弹幕消息
                if (!isHandleMsgListenerNull()) {
                    danmuMsgListener.handleDanmuMsg(msg.get("txt").toString());
                }
            }
            //@TODO 其他业务信息根据需要进行添加

            /*************************************************************/
        }
    }
}
