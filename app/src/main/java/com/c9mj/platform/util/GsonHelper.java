package com.c9mj.platform.util;

import com.google.gson.Gson;

/**
 * author: LMJ
 * date: 2016/9/1
 */
public class GsonHelper {
	private static Gson gson;
	public static	Object obj = null;
	//序列化所有注解的字段
	private static Gson getInstance() {
		synchronized (GsonHelper.class) {
			if (gson == null) {
				gson = new Gson();
			}
		}
		return gson;
	}

	/**
	 * 
	* @Title: parseJson 
	* @Description: json 转成对象 
	* @param jsonStr
	* @param clazz
	* @return
	* @throws
	 */
	public static Object parseJson(String jsonStr,Class clazz){
		gson = getInstance();
		obj = gson.fromJson(jsonStr, clazz);
		return obj;
	}

	/**
	 * 
	* @Title: toJson 
	* @Description: 对象转成json
	* @param obj
	* @return
	* @throws
	 */
	public static String toJson(Object obj){
		String strJson = "";
		gson = getInstance();
		strJson = gson.toJson(obj);
		return  strJson;
	}
	
}
