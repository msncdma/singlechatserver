package com.anlong.chatserver.util;

import org.springframework.context.ApplicationContext;



public class ApplicationContextBean {

	private static ApplicationContext context = null;

	private final static String CONFIG_FILE_NAME = "applicationContext.xml";
	static {
//		context = new ClassPathXmlApplicationContext(CONFIG_FILE_NAME);
	}
	
	public static void main(String[] args) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("user_id", 121761);
//		MwImUserInfoDAO mwImUserInfoDAO = (MwImUserInfoDAO)context.getBean("mwImUserInfoDAO");
//		boolean b = mwImUserInfoDAO.getMwImUserInfoIsExist(map);
//		System.out.println(b);
	}
}
