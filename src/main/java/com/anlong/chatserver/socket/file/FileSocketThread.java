package com.anlong.chatserver.socket.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.mina.core.session.IoSession;

import com.anlong.chatserver.entity.common.FileSendInfo;


public class FileSocketThread extends Thread{
	// 文件传输对象
	protected FileSendInfo fileSendInfo = null;
	// socket输入流
	protected DataInputStream bis = null;
	// socket输出流
	protected DataOutputStream bos = null;
	// session
	protected IoSession session = null;
	// 业务处理类
	private FileStreamService fileStreamService;
	
	public FileSocketThread(IoSession session,InputStream in, OutputStream out) {
		this.session = session;
		bis = new DataInputStream(in);
		bos = new DataOutputStream(out);
	}

	
	@Override
	public void run() {
		try {
			fileSendInfo = new FileSendInfo();
			
			fileSendInfo.setOperateType(bis.readByte());
			if (fileSendInfo.getOperateType() == 1) {  //上传文件
				fileStreamService = new FileUploadService();
			}else if (fileSendInfo.getOperateType() == 2) {  //下载文件
				fileStreamService = new FileDownloadService();
			}else if (fileSendInfo.getOperateType() == 3) {  //上传图片
				fileStreamService = new ImgUploadService();
			}else if (fileSendInfo.getOperateType() == 4) {  //上传音频
				fileStreamService = new AudioUploadService();
			}
			fileStreamService.parseMsg(bis, bos, fileSendInfo);
			fileStreamService.execute(session,bis, bos, fileSendInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
