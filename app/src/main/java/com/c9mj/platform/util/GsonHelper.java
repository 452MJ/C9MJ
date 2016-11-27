package com.c9mj.platform.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * author: LMJ
 * date: 2016/9/1
 * Gson工具类
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
	 * @Title: parseJson
	 * @Description: json 转成对象
	 * @param jsonElement
	 * @param clazz
	 * @return
	 * @throws
	 */
	public static Object parseJson(JsonElement jsonElement, Class clazz){
		gson = getInstance();
		obj = gson.fromJson(jsonElement, clazz);
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
