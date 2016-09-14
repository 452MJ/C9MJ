package com.c9mj.platform.live.danmu.msg;

/**
 * author: LMJ
 * date: 2016/9/14
 * socket长连接的数据包编码类
 */
public class DanmuEncoder {
    private StringBuffer buf = new StringBuffer();

    /**
     * 返回弹幕协议格式化后的结果
     * @return
     */
    public String getResult()
    {
        //数据包末尾必须以'\0'结尾
        buf.append('\0');
        return buf.toString();
    }

    /**
     * 添加协议参数项
     * @param key
     * @param value
     */
    public void addItem(String key, Object value)
    {
        //根据斗鱼弹幕协议进行相应的编码处理
        buf.append(key.replaceAll("/", "@S").replaceAll("@", "@A"));
        buf.append("@=");
        if(value instanceof String){
            buf.append(((String)value).replaceAll("/", "@S").replaceAll("@", "@A"));
        }else if(value instanceof Integer){
            buf.append(value);
        }
        buf.append("/");
    }
}
