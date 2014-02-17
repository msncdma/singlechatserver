package com.anlong.chatserver.service;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.anlong.chatserver.socket.chat.ChatSocketServer;
import com.anlong.chatserver.util.SpringUtil;


public class InitSystemService implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		//初始化Spring
		SpringUtil.setServletContext(servletContext);
		// 初始化socket服务
		this.initSocketServer();
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}
	private void initSocketServer() {
		new Thread(new ChatSocketServer()).start();
		// new Thread(new FileSocketServer()).start();
	}

	
}
