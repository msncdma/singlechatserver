package com.anlong.chatserver.entity.common;


/**
 * @Title: OnlineState.java 
 * @Package com.anlong.chatserver.entity.common
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午4:20:27 
 * @version V1.0   
 * @Description: TODO
 */
public class OnlineState {
	/** 用户编号 */
	private Integer userId;
	/**
	 * 在线方式 1－IM客户端在线； 2－手机WAP在线； 3－WEB网页在线；4－苹果手机IM；5－安卓手机IM；81－手机短信上行；91－移动OA；
	 */
	private Byte onlineType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Byte getOnlineType() {
		return onlineType;
	}

	public void setOnlineType(Byte onlineType) {
		this.onlineType = onlineType;
	}

	@Override
	public String toString() {
		return "OnlineState [userId=" + userId + ", onlineType=" + onlineType + "]";
	}

}
