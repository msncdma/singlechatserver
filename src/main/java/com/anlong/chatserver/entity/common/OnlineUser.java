package com.anlong.chatserver.entity.common;

import java.util.Date;

import org.apache.mina.core.session.IoSession;


/**
 * @Title: OnlineUser.java 
 * @Package com.anlong.chatserver.entity.common
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午4:21:10 
 * @version V1.0   
 * @Description: 在线用户封装类,用于在线用户内存管理
 */
public class OnlineUser {
	/** 用户ID */
	private int userId;
	/** 部门ID */
	private int depId;
	/** 企业ID */
	private String corpCode;
	/** 在线状态0退出 1 在线 2忙碌 3离开 4隐身 */
	private byte onlineState;
	/** 用户类型 */
	private byte userType;
	/** 用户名 */
	private String name;
	/** 头像地址 */
	private String headUrl;
	/** 性别 */
	private int sex;
	/** 签名 */
	private String signature;
	/** 手机 */
	private String mobile;
	/** 用户令牌 */
	private Integer key;
	/** 用户登录方式 */
	private byte loginType;
	/** 登录地址 */
	private String ipAddress;
	/** 用户设备MAC地址 */
	private String macAddress;
	/** 备注信息 */
	private String remark;
	/** 客户端版本号 */
	private String clientVersion;
	/** 登录时间 */
	private Date loginTime;
	/** 最后活动时间 */
	private Long lastActiveTime;
	/** 用户session */
	private IoSession session;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDepId() {
		return depId;
	}

	public void setDepId(int depId) {
		this.depId = depId;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public byte getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(byte onlineState) {
		this.onlineState = onlineState;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public byte getLoginType() {
		return loginType;
	}

	public void setLoginType(byte loginType) {
		this.loginType = loginType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Long getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Long lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	@Override
	public String toString() {
		return "OnlineUser [userId=" + userId + ", depId=" + depId + ", corpCode=" + corpCode + ", onlineState=" + onlineState + ", userType=" + userType + ", name=" + name + ", headUrl=" + headUrl + ", sex=" + sex + ", signature=" + signature
				+ ", mobile=" + mobile + ", key=" + key + ", loginType=" + loginType + ", ipAddress=" + ipAddress + ", macAddress=" + macAddress + ", remark=" + remark + ", clientVersion=" + clientVersion + ", loginTime=" + loginTime
				+ ", lastActiveTime=" + lastActiveTime + ", session=" + session + "]";
	}

}
