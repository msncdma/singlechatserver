package com.anlong.chatserver.socket.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.mina.core.session.IoSession;

import com.anlong.chatserver.constant.StaticValue;
import com.anlong.chatserver.entity.common.FileSendInfo;


public class FileDownloadService implements FileStreamService{



	public void parseMsg(DataInputStream bis, DataOutputStream bos,FileSendInfo fileSendInfo) {
		try {
			// 用户编号
			fileSendInfo.setReceiveId(bis.readInt());
			
			// 文件编号
			short fileCodeLength = bis.readShort();
			byte[] fileCodeArr = new byte[fileCodeLength];
			bis.read(fileCodeArr);
			fileSendInfo.setFileCode(new String(fileCodeArr,StaticValue.CHARSET_NAME));
			
			// 文件大小
			fileSendInfo.setFileSize(bis.readInt());
			
			
			/** 磁盘目录*/
			String fileCode = fileSendInfo.getFileCode();
			String absolutePath = StaticValue.FILE_SAVE_PATH;
			if(fileCode != null && fileCode.length() >= 6){
				String year = fileCode.substring(0,4);
				String month = fileCode.substring(4, 6);
				String day = fileCode.substring(6, 8);
				absolutePath += "/"+year+"/"+month+"/"+day+"/";
			}
			fileSendInfo.setAbsolutePath(absolutePath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public void execute(IoSession session,DataInputStream bis, DataOutputStream bos,FileSendInfo fileSendInfo) {
		try {
			int waitCount = 0;
			int receiveLength = 0;
			int tempData = 0;
			byte[] bufferByte = new byte[StaticValue.FILE_WRITE_SIZE];
			
			File sendFile = new File(fileSendInfo.getAbsolutePath()+fileSendInfo.getFileCode()); 
			//没有文件  等待发送方发送 最多等待10秒钟
			while(!sendFile.exists() && !session.isClosing()){
				if(waitCount > StaticValue.FILE_WAIT_COUNT) break;
				waitCount ++;
				Thread.sleep(StaticValue.FILE_WAIT_MILLIS);
			}
			
			if(sendFile.exists()){
				FileInputStream fis = new FileInputStream(sendFile);
				BufferedInputStream fileInputStream = new BufferedInputStream(fis);  
				// 已读取的文件长度
				int readFileLength = 0;
				// 第一回合 服务器主动发送到客户端，其他等客户端全部接收完成再发送
				Boolean firstRoundFlag = true;
				
				while ( fileSendInfo.getFileSize() > receiveLength ) {
					if(firstRoundFlag || receiveLength >= readFileLength){
						firstRoundFlag = false;
						if((tempData = fileInputStream.read(bufferByte)) != -1){
							readFileLength = readFileLength + tempData;
							bos.write(bufferByte, 0, tempData);
							bos.flush();
						} 
					}else {
						// 读取客户端返回的进度信息
						receiveLength = bis.readInt();
					}
				}
				
			}else{
				// TODO: 文件接收超时
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	
}
