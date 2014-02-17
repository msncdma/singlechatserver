package com.anlong.chatserver.entity.common;


/**
 * @Title: FileSendInfo.java 
 * @Package com.anlong.chatserver.entity.common
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午5:47:27 
 * @version V1.0   
 * @Description: TODO
 */
public class FileSendInfo {

	/** 操作类型 1上传文件 2下载文件 3上传图片 4音频文件 */
	private Byte operateType;
	/** 消息来源 */
	private Byte apid;
	/** 发送类型 1个人发送 2机构发送 3互动群 4讨论组 */
	private Byte sendType;
	/** 上传模式 1在线传输 2离线传输 3点对点传输 */
	private Byte fileMode;
	/** 发送方编号 */
	private Integer sendId;
	/** 接收方编号 */
	private Integer receiveId;
	/** 文件编码 */
	private String fileCode;
	/** 文件名称 */
	private String fileName;
	/** 文件大小 */
	private Integer fileSize;
	/** 文件路径 */
	private String absolutePath;
	/** 图片类型 1 聊天图片 2个人头像 3群组头像 */
	private Byte imageType;
	/** 音频类型 1 个人 2 群组 */
	private Byte audioType;

	public Byte getOperateType() {
		return operateType;
	}

	public void setOperateType(Byte operateType) {
		this.operateType = operateType;
	}

	public Byte getApid() {
		return apid;
	}

	public void setApid(Byte apid) {
		this.apid = apid;
	}

	public Byte getSendType() {
		return sendType;
	}

	public void setSendType(Byte sendType) {
		this.sendType = sendType;
	}

	public Byte getFileMode() {
		return fileMode;
	}

	public void setFileMode(Byte fileMode) {
		this.fileMode = fileMode;
	}

	public Integer getSendId() {
		return sendId;
	}

	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}

	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

	public String getFileCode() {
		return fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileSize() {
		return fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public Byte getImageType() {
		return imageType;
	}

	public void setImageType(Byte imageType) {
		this.imageType = imageType;
	}

	public Byte getAudioType() {
		return audioType;
	}

	public void setAudioType(Byte audioType) {
		this.audioType = audioType;
	}

	@Override
	public String toString() {
		return "FileSendInfo [operateType=" + operateType + ", apid=" + apid + ", sendType=" + sendType + ", fileMode=" + fileMode + ", sendId=" + sendId + ", receiveId=" + receiveId + ", fileCode=" + fileCode + ", fileName=" + fileName
				+ ", fileSize=" + fileSize + ", absolutePath=" + absolutePath + ", imageType=" + imageType + ", audioType=" + audioType + "]";
	}

}
