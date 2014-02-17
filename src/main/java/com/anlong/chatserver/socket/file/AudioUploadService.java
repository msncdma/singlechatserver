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
import com.anlong.chatserver.util.Utils;

public class AudioUploadService implements FileStreamService {

	
	public void parseMsg(DataInputStream bis, DataOutputStream bos, FileSendInfo fileSendInfo) {
		try {
			// 文件编号
			short fileCodeLength = bis.readShort();
			byte[] fileCodeArr = new byte[fileCodeLength];
			bis.read(fileCodeArr);
			fileSendInfo.setFileCode(new String(fileCodeArr, StaticValue.CHARSET_NAME));

			// 文件类型
			short fileTypeLength = bis.readShort();
			byte[] fileTypeArr = new byte[fileTypeLength];
			bis.read(fileTypeArr);
			fileSendInfo.setFileName(new String(fileTypeArr, StaticValue.CHARSET_NAME));
			// 文件大小
			fileSendInfo.setFileSize(bis.readInt());
			// 音频类型 1 个人语音 2 群组语音
			fileSendInfo.setAudioType(bis.readByte());
			// 音频文件路径
			switch (fileSendInfo.getAudioType()) {
			case StaticValue.AUDIO_TIPE_PERSONAL:
				fileSendInfo.setAbsolutePath(Utils.getWebRoot() + StaticValue.PERSONAL_AUDIO_PATH);
				break;
			case StaticValue.AUDIO_TIPE_GROUP:
				fileSendInfo.setAbsolutePath(Utils.getWebRoot() + StaticValue.GROUP_AUDIO_PATH);
				break;
			default:
				break;
			}

			// fileSendInfo.setAbsolutePath(Utils.getWebRoot()+StaticValue.FILE_IMAGE_ORIGINAL_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute(IoSession session, DataInputStream bis, DataOutputStream bos, FileSendInfo fileSendInfo) {
		FileOutputStream fos = null;
		BufferedOutputStream fileOutputStream = null;
		try {
			int receiveLength = 0;
			// 每次实际读取的长度
			int tempData = 0;
			// 每次读取字节字节数组
			byte[] bufferByte = new byte[StaticValue.FILE_BUFFER_SIZE];
			// 动态设定路径
			File receiveFile = new File(fileSendInfo.getAbsolutePath() + fileSendInfo.getFileCode() + "." + fileSendInfo.getFileName());// 路径+文件名
			// 磁盘文件输出流
			fos = new FileOutputStream(receiveFile);
			fileOutputStream = new BufferedOutputStream(fos);
			while (fileSendInfo.getFileSize() > receiveLength) {
				// 读物文件流程 写入到磁盘
				if ((tempData = bis.read(bufferByte)) != -1) {
					// 设置服务器收到的数据长度
					receiveLength = receiveLength + tempData;
					// 写入文件
					fileOutputStream.write(bufferByte, 0, tempData);
					fileOutputStream.flush();
					
				}
			}
			// 写入成功消息
			bos.writeInt(receiveLength);
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileOutputStream.close();
				fos.close();
			} catch (IOException e) {
			}
		}

	}

}
