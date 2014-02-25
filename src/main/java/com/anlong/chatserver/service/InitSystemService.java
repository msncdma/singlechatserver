package com.anlong.chatserver.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.anlong.chatserver.socket.chat.ChatSocketServer;


public class InitSystemService implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		//ServletContext servletContext = sce.getServletContext();
		//SpringUtil.setServletContext(servletContext);
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
