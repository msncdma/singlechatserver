package com.anlong.chatserver.entity.response;


public class BaseResponse /* implements Serializable */{
	/** 消息长度 （字节为单位） */
	private Integer msgSize;
	/** 接口业务编码 */
	private Short bCode;
	/** 密钥，加密用 */
	private Integer key;
	/** 用户ID */
	private Integer uid;
	/** 应答代码 */
	private Short rtCode;
	/** 错误信息 */
	private String rtMsg;
	/** 请求流水号 */
	private Integer msgSerial;

	public Integer getMsgSize() {
		return msgSize;
	}

	public void setMsgSize(Integer msgSize) {
		this.msgSize = msgSize;
	}

	public Short getbCode() {
		return bCode;
	}

	public void setbCode(Short bCode) {
		this.bCode = bCode;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Short getRtCode() {
		return rtCode;
	}

	public void setRtCode(Short rtCode) {
		this.rtCode = rtCode;
	}

	public String getRtMsg() {
		return rtMsg;
	}

	public void setRtMsg(String rtMsg) {
		this.rtMsg = rtMsg;
	}

	public Integer getMsgSerial() {
		return msgSerial;
	}

	public void setMsgSerial(Integer msgSerial) {
		this.msgSerial = msgSerial;
	}

	@Override
	public String toString() {
		return "BaseResponse [msgSize=" + msgSize + ", bCode=" + bCode + ", key=" + key + ", uid=" + uid + ", rtCode=" + rtCode + ", rtMsg=" + rtMsg + ", msgSerial=" + msgSerial + "]";
	}

}
