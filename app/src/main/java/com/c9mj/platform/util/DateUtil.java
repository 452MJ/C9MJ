package com.c9mj.platform.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class DateUtil {

    /**
     * 时间戳转换成日期格式字符串
     * @param timeStamp 精确到秒的字符串如1417792627
     * @param format 日期格式如默认"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String timeStamp2Date(String timeStamp,String format) {
        if(timeStamp == null || timeStamp.isEmpty() || timeStamp.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(timeStamp)));
    }

    /**
     * 时间戳获取星期
     * @param timeStamp 精确到秒的字符串如1417792627
     * @return 星期天返回1 星期一返回2 ...
     */
    public static int timeStamp2Week(String timeStamp){
        if(timeStamp == null || timeStamp.isEmpty() || timeStamp.equals("null")){
            return -1;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(Long.valueOf(timeStamp)));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 日期格式字符串转换成时间戳
     * @param dateStr 具体的字符串日期如"2016-10-10 10:10:10"
     * @param format 日期格式如"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    public static String date2TimeStamp(String dateStr,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
