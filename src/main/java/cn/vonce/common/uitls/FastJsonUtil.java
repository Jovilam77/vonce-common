package cn.vonce.common.uitls;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * fastjson 工具类
 * 
 * @author Jovi
 * @email 766255988@qq.com
 * @version 1.0
 * @date 2017年10月10日下午5:44:08
 */
public class FastJsonUtil {

	/**
	 * 对象转Json字符串
	 * 
	 * @author Jovi
	 * @date 2017年10月10日下午5:44:19
	 * @param obj
	 * @return
	 */
	public static String obj2Json(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * json字符串转对象
	 * 
	 * @author Jovi
	 * @date 2017年10月10日下午5:44:35
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T> T json2Obj(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	/**
	 * json字符串转List
	 * 
	 * @author Jovi
	 * @date 2017年10月10日下午5:44:55
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> json2List(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

}
