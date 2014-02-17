package com.anlong.chatserver.socket.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.apache.mina.core.session.IoSession;

import com.anlong.chatserver.entity.common.FileSendInfo;


/**
 * @Title: FileStreamService.java 
 * @Package com.anlong.chatserver.socket.file
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午5:51:35 
 * @version V1.0   
 * @Description: TODO
 */
public interface FileStreamService {
	
	/** 数据解析方法*/
	public void parseMsg(DataInputStream bis,DataOutputStream bos, FileSendInfo fileSendInfo);

	/** 文件处理方法*/
	public void execute(IoSession session,DataInputStream bis, DataOutputStream bos,FileSendInfo fileSendInfo);
}
