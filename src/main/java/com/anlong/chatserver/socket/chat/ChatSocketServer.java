package com.anlong.chatserver.socket.chat;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.anlong.chatserver.util.SystemGlobals;



public class ChatSocketServer implements Runnable {

	public void run() {
		final IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.setHandler(new ChatSocketHandle());
		// 添加编码过滤器
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new BeanCodecFactory()));

		try {
			int port = SystemGlobals.getIntValue("anlongs.im.chat.port", 3001);
			acceptor.bind(new InetSocketAddress(port));
			System.out.println("socket start.........");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
