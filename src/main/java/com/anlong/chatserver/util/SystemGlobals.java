package com.anlong.chatserver.util;

import java.util.ResourceBundle;


/**
 * @Title: SystemGlobals.java 
 * @Package com.anlong.chatserver.util
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午2:30:01 
 * @version V1.0   
 * @Description: TODO
 */
public class SystemGlobals {
	private static ResourceBundle rb = null;
	public static final String SYSTEM_GLOBALS_NAME = "SystemGlobals";
	
	static{
		rb = ResourceBundle.getBundle(SYSTEM_GLOBALS_NAME);
	}

	public static String getValue(String key) {
		return rb.getString(key);
	}

	public static String getValue(String key, String defaultValue) {
		String value = rb.getString(key);
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		return value;
	}

	public static int getIntValue(String key, int defaultValue) {
		if (rb.getString(key) == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(rb.getString(key));
		} catch (Exception e) {
			return defaultValue;
		}
	}
}
