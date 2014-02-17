package com.anlong.chatserver.socket.chat;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


public class BeanCodecFactory implements ProtocolCodecFactory{

	
	private  BeanCodecDecoder decoder;
	private  BeanCodecEncoder encoder;
	
	public BeanCodecFactory(){
		decoder = new BeanCodecDecoder();
		encoder = new BeanCodecEncoder();
	}
	
	public ProtocolDecoder getDecoder(IoSession arg0) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession arg0) throws Exception {
		return encoder;
	}

}
