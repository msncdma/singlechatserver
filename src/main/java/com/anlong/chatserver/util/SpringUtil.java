package com.anlong.chatserver.util;

import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * 
 * SpringUtil
 * 
 * al
 * al
 * 2014年2月17日 下午2:17:24
 * 
 * @version 1.0.0
 * 
 */
public class SpringUtil {
	
	private static ServletContext servletContext;
	private static WebApplicationContext webApplictionContext;
	
	public static Object getBean(String name){
		return webApplictionContext.getBean(name);
	}
	
	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		SpringUtil.servletContext = servletContext;
		SpringUtil.webApplictionContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}

	public static ApplicationContext getApplicationContext() throws BeansException, IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		ApplicationContext ctx = new FileSystemXmlApplicationContext("/WebRoot/WEB-INF/applicationContext.xml");
		//ApplicationContext ctx = new FileSystemXmlApplicationContext("../webapps/War/WEB-INF/applicationContext.xml");
		return ctx;
	}
	
}
