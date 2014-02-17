package com.anlong.chatserver.socket.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.anlong.chatserver.constant.StaticValue;
import com.anlong.chatserver.entity.common.FileSendInfo;
import com.anlong.chatserver.util.Utils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class ImgUploadService implements FileStreamService{
	Logger log = Logger.getLogger(ImgUploadService.class);
	private int outputWidth = 100; // 默认输出图片宽
	private int outputHeight = 100; // 默认输出图片高
	private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)
	
	public void parseMsg(DataInputStream bis, DataOutputStream bos,FileSendInfo fileSendInfo) {
		try {
			// 文件编号
			short fileCodeLength = bis.readShort();
			byte[] fileCodeArr = new byte[fileCodeLength];
			bis.read(fileCodeArr);
			fileSendInfo.setFileCode(new String(fileCodeArr,StaticValue.CHARSET_NAME));
			
			// 文件类型 
			short fileTypeLength = bis.readShort();
			byte[] fileTypeArr = new byte[fileTypeLength];
			bis.read(fileTypeArr);
			fileSendInfo.setFileName(new String(fileTypeArr,StaticValue.CHARSET_NAME));
			
			// 文件大小
			fileSendInfo.setFileSize(bis.readInt());
			
			// 图片类型   1 聊天图片   2个人头像    3群组头像
			fileSendInfo.setImageType(bis.readByte());
			// 文件路径
			switch (fileSendInfo.getImageType()) {
				case StaticValue.IMAGE_TIPE_MESSAGE : fileSendInfo.setAbsolutePath(Utils.getWebRoot()+StaticValue.FILE_IMAGE_ORIGINAL_PATH);
					break;
				case StaticValue.IMAGE_TIPE_HEAD: fileSendInfo.setAbsolutePath(Utils.getWebRoot()+StaticValue.FILE_HEAD_IMGAGE_PATH);
					break;
				case StaticValue.IMAGE_TIPE_GROUP: fileSendInfo.setAbsolutePath(Utils.getWebRoot()+StaticValue.FILE_GROUP_IMAGE_PATH);
					break;
				default:
					break;
			}
			log.info("收到图片上传请求" + fileSendInfo.toString());
			
//			fileSendInfo.setAbsolutePath(Utils.getWebRoot()+StaticValue.FILE_IMAGE_ORIGINAL_PATH);
		} catch (Exception e) {
			log.error("parseMsg error",e);
		}
	}
	

	public void execute(IoSession session,DataInputStream bis, DataOutputStream bos,FileSendInfo fileSendInfo) {
		FileOutputStream fos = null;
		try {
			int receiveLength = 0;
			// 每次实际读取的长度
			int tempData = 0;
			// 每次读取字节字节数组
			byte[] bufferByte = new byte[StaticValue.FILE_BUFFER_SIZE];
			
			//动态设定路径
			File receiveFile = new File(fileSendInfo.getAbsolutePath()+fileSendInfo.getFileCode()+"."+fileSendInfo.getFileName());//路径+文件名
			//磁盘文件输出流
			fos = new FileOutputStream(receiveFile);
			log.info("图片上传路径" + fileSendInfo.getAbsolutePath()+fileSendInfo.getFileCode()+"."+fileSendInfo.getFileName());
			BufferedOutputStream fileOutputStream=new BufferedOutputStream(fos);
			while(fileSendInfo.getFileSize() > receiveLength){
				//读物文件流程  写入到磁盘
				if((tempData = bis.read(bufferByte)) != -1){
					// 设置服务器收到的数据长度
					receiveLength = receiveLength + tempData;  
					// 写入文件
					fileOutputStream.write(bufferByte, 0, tempData);  
					fileOutputStream.flush(); 
					if(receiveLength >= fileSendInfo.getFileSize()){
						// 压缩聊天图片
						if(fileSendInfo.getImageType() == StaticValue.IMAGE_TIPE_MESSAGE){
							this.compressPic(receiveFile, fileSendInfo);
						}
						
						// 写入成功消息
						bos.writeInt(receiveLength);
						bos.flush();
					}
				}
			}
		} catch (Exception e) {
			log.error("ImgUploadService.execute error",e);
			e.printStackTrace();
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
			}
		}
		
	}
	
	
	 // 图片处理 
	 public void compressPic(File file,FileSendInfo fileSendInfo) { 
		 try { 
			 if(file.exists()){
				 Image img = ImageIO.read(file); 
				 // 判断图片格式是否正确 
				 if (img.getWidth(null) == -1) {
					 return; 
				} else {
					int newWidth;
					int newHeight;
					// 判断是否是等比缩放
					if (this.proportion == true) {
						// 为等比缩放计算输出的图片宽度及高度
						double rate1 = ((double) img.getWidth(null)) / (double) outputWidth + 0.1;
						double rate2 = ((double) img.getHeight(null)) / (double) outputHeight + 0.1;
						// 根据缩放比率大的进行缩放控制
						double rate = rate1 > rate2 ? rate1 : rate2;
						newWidth = (int) (((double) img.getWidth(null)) / rate);
						newHeight = (int) (((double) img.getHeight(null)) / rate);
					} else {
						newWidth = outputWidth; // 输出的图片宽度
						newHeight = outputHeight; // 输出的图片高度
					}
					BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);

					/*
					 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好
					 * 但速度慢
					 */
					tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
					FileOutputStream out = new FileOutputStream(Utils.getWebRoot() + StaticValue.FILE_IMAGE_SMALL_PATH + fileSendInfo.getFileCode() + "." + fileSendInfo.getFileName());
					// JPEGImageEncoder可适用于其他图片类型的转换
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					encoder.encode(tag);
					out.close();
				}
				 
			 }
		 } catch (IOException ex) { 
			 log.error("ImgUploadService.compressPic error",ex);
		 } 
	} 
	
	
	
}
