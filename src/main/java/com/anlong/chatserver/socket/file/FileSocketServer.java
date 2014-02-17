package com.anlong.chatserver.socket.file;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.anlong.chatserver.util.SystemGlobals;




/**
 * @Title: FileSocketServer.java 
 * @Package com.anlong.chatserver.socket.file
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午5:52:18 
 * @version V1.0   
 * @Description: TODO
 */
public class FileSocketServer implements Runnable {
	private static Logger logger = Logger.getLogger(FileSocketServer.class);
	
	public void run() {
		// 建立一个无阻塞服务端socket,用nio
		NioSocketAcceptor acceptor = new NioSocketAcceptor();
		// 创建接收过滤器 也就是你要传送对象的类型
		// DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

		// 设定对象传输工厂
		ObjectSerializationCodecFactory factory = new ObjectSerializationCodecFactory();

		// 设定后服务器可以接收大数据
		factory.setDecoderMaxObjectSize(Integer.MAX_VALUE);
		factory.setEncoderMaxObjectSize(Integer.MAX_VALUE);
		//这个用于打印日志,可以不写
		// chain.addLast("logging", new LoggingFilter());

		// 设定服务端消息处理器
		acceptor.setHandler(new FileSocketHandle());
		InetSocketAddress inetSocketAddress = null;

		try {
			int port = SystemGlobals.getIntValue("anlongs.im.chat.port", 3001)+1;
			inetSocketAddress = new InetSocketAddress(port);
			acceptor.bind(inetSocketAddress);
//			logger.debug("File Socket Server Started;Port=" + PORT);
		} catch (IOException e) {
			logger.error(e);
		}
	}
}
