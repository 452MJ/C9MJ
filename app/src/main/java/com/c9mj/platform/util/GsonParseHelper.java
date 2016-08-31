package com.c9mj.platform.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
* 项目名称：DTEduAppBaseLib   
* 类名称：GsonParseHelper   
* 类描述：   Gson解析帮助类
* 创建人：Administrator   
* 创建时间：2014-4-9 下午4:03:22   
* 修改人：Administrator   
* 修改时间：2014-4-9 下午4:03:22   
* 修改备注：   
* @version    
*
 */
public class GsonParseHelper {

	private static Gson gson;
	private static Gson builderGson;
	public static	Object obj = null;
	//序列化所有注解的字段
	public static Gson getInstance() {
		synchronized (GsonParseHelper.class) {
			if (gson == null) {
				gson = new Gson();
			}
		}
		return gson;
	}
	
	 // 不序列化没有 @Expose 注解的字段   
	public static Gson getBuilderInstance(){
		synchronized( GsonParseHelper.class ) {
			if( builderGson == null ) {
				GsonBuilder builder = new GsonBuilder();
				builder.excludeFieldsWithoutExposeAnnotation();
				builderGson = builder.create();
			}
		}
		return builderGson;
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
