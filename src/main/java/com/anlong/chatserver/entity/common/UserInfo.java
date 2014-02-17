package com.anlong.chatserver.entity.common;

/**
 * @Title: UserInfo.java 
 * @Package com.anlong.chatserver.entity.common
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午4:21:45 
 * @version V1.0   
 * @Description: 详细用户信息实体类,用于参数传递
 */
public class UserInfo {
	/** 用户ID */
	private Integer userId;
	/** 机构ID */
	private Integer depId;
	/** 用户类型 */
	private Byte userType;
	/** 在线状态0退出 1 在线 2忙碌 3离开 4隐身 */
	private Byte onlineState;
	/** 登录名称 */
	private String userName;
	/** 用户名 */
	private String name;
	/** 头像地址 */
	private String headUrl;
	/** 签名 */
	private String signature;
	/** 0女 1男 */
	private Byte sex;
	/** 生日 */
	private String birthday;
	/** 手机 */
	private String mobile;
	/** 办公电话 */
	private String telephone;
	/** 企鹅账号 */
	private String qq;
	/** 电子邮箱 */
	private String email;
	/** MSN */
	private String msn;
	/** 短信余额 */
	private Integer smsBalance;
	/** 彩信余额 */
	private Integer mmsBalance;
	/** 职位*/
	private String position;
	/** 岗位*/
	private String job;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public Byte getUserType() {
		return userType;
	}

	public void setUserType(Byte userType) {
		this.userType = userType;
	}

	public Byte getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(Byte onlineState) {
		this.onlineState = onlineState;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Byte getSex() {
		return sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public Integer getSmsBalance() {
		return smsBalance;
	}

	public void setSmsBalance(Integer smsBalance) {
		this.smsBalance = smsBalance;
	}

	public Integer getMmsBalance() {
		return mmsBalance;
	}

	public void setMmsBalance(Integer mmsBalance) {
		this.mmsBalance = mmsBalance;
	}
	

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", depId=" + depId + ", userType=" + userType + ", onlineState=" + onlineState + ", userName="
				+ userName + ", name=" + name + ", headUrl=" + headUrl + ", signature=" + signature + ", sex=" + sex + ", birthday=" + birthday
				+ ", mobile=" + mobile + ", telephone=" + telephone + ", qq=" + qq + ", email=" + email + ", msn=" + msn + ", smsBalance="
				+ smsBalance + ", mmsBalance=" + mmsBalance + "]";
	}

}
