package com.anlong.chatserver.entity.response;

import com.anlong.chatserver.entity.common.UserInfo;


public class Response1010 extends BaseResponse {

	/** 密钥 */
	private Integer key;
	/** 服务器时间*/
	private String dateTime;
	/** 用户基本信息 */
	private UserInfo userInfo;

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "Response1010 [key=" + key + ", dateTime=" + dateTime + ", userInfo=" + userInfo + "]" + super.toString();
	}

}
