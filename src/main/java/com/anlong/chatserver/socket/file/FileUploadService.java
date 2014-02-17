package com.anlong.chatserver.socket.file;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.mina.core.session.IoSession;

import com.anlong.chatserver.constant.StaticValue;
import com.anlong.chatserver.entity.common.FileSendInfo;


public class FileUploadService implements FileStreamService{

	
	
	public void parseMsg(DataInputStream bis, DataOutputStream bos,FileSendInfo fileSendInfo) {

		try {
			// 发送类型
			fileSendInfo.setSendType(bis.readByte()); 
			
			fileSendInfo.setFileMode(bis.readByte());
			fileSendInfo.setApid(bis.readByte());
			fileSendInfo.setSendId(bis.readInt());
			fileSendInfo.setReceiveId(bis.readInt());
			short fileCodeLength = bis.readShort();
			byte[] fileCodeArr = new byte[fileCodeLength];
			bis.read(fileCodeArr);
			fileSendInfo.setFileCode(new String(fileCodeArr,StaticValue.CHARSET_NAME));
			
			short fileNameLength = bis.readShort();
			byte[] fileNameArr = new byte[fileNameLength];
			bis.read(fileNameArr);
			fileSendInfo.setFileName(new String(fileNameArr,StaticValue.CHARSET_NAME));
			
			fileSendInfo.setFileSize(bis.readInt());
			
			
			/** 创建磁盘目录*/
			String fileCode = fileSendInfo.getFileCode();
			int progressLength = fileSendInfo.getFileSize() / 100;
			String absolutePath = StaticValue.FILE_SAVE_PATH;
			
			// 返回接收进度最大值
			if(progressLength > StaticValue.FILE_SEND_SIZE){
				progressLength = StaticValue.FILE_SEND_SIZE;
			}
			// 返回接收进度最小值
			if(progressLength < StaticValue.FILE_BUFFER_SIZE){
				progressLength = StaticValue.FILE_BUFFER_SIZE;
			}
			
			if(fileCode != null && fileCode.length() >= 6){
				String year = fileCode.substring(0,4);
				String month = fileCode.substring(4,6);
				String day = fileCode.substring(6,8);
				absolutePath += "/"+year+"/"+month+"/"+day+"/";
			}
			
			fileSendInfo.setAbsolutePath(absolutePath);
			
			File file = new File(absolutePath);
			if (!file.exists()){
				file.mkdirs();   //创建磁盘路径
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public void execute(IoSession session,DataInputStream bis, DataOutputStream bos,FileSendInfo fileSendInfo) {
		try {
			//当前回合进度大小
			int tempProgress = 0;
			// 是否需要返回进度值
			boolean tempSendFlag = false;
			int receiveLength = 0;
			int tempData = 0;
			int tempLength = 0;
			byte[] bufferByte = new byte[StaticValue.FILE_BUFFER_SIZE];
			int progressLength = fileSendInfo.getFileSize() / 100;
			
			// 返回接收进度最大值
			if(progressLength > StaticValue.FILE_SEND_SIZE){
				progressLength = StaticValue.FILE_SEND_SIZE;
			}
			// 返回接收进度最小值
			if(progressLength < StaticValue.FILE_BUFFER_SIZE){
				progressLength = StaticValue.FILE_BUFFER_SIZE;
			}
			
			//动态设定路径
			File receiveFile = new File(fileSendInfo.getAbsolutePath()+fileSendInfo.getFileCode());//路径+文件名
			//磁盘文件输出流
			FileOutputStream fos = new FileOutputStream(receiveFile);
			BufferedOutputStream fileOutputStream=new BufferedOutputStream(fos);
			
			
			while(fileSendInfo.getFileSize()>receiveLength){
				//读物文件流程  写入到磁盘
				if((tempData = bis.read(bufferByte)) != -1){
					// 设置服务器收到的数据长度
					receiveLength = receiveLength + tempData;  
					tempLength = tempLength+tempData;
					tempProgress = tempProgress+tempData;
					tempSendFlag = true;
					// 写入文件
					fileOutputStream.write(bufferByte, 0, tempData);  
					fileOutputStream.flush(); 
					
					// 向客户端返回收到的长度
					if(tempLength >= StaticValue.FILE_SEND_SIZE || receiveLength >= fileSendInfo.getFileSize()){
						 tempProgress = 0;
						 tempSendFlag = true;
						 tempLength = tempLength - StaticValue.FILE_SEND_SIZE;
						 
						 //发送消息给客户端 当前收到数据长度  
						 bos.writeInt(receiveLength);
						 bos.flush();
					}
					
					//发送进度消息 如已经发送回合消息则不再反进度消息
					if(tempProgress > progressLength && !tempSendFlag){
						tempProgress = 0;
						//发送消息给客户端 当前收到数据长度 
						bos.writeInt(receiveLength);
						bos.flush();
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
