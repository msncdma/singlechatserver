package com.anlong.chatserver.constant;

import com.anlong.chatserver.util.SystemGlobals;


/**
 * @Title: StaticValue.java 
 * @Package com.anlong.chatserver.constant
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午3:52:54 
 * @version V1.0   
 * @Description: TODO
 */
public class StaticValue {
	// 协议长度占用字节数
	public static final Integer PROTOCOL_SIZE = 4;
	// 协议字符串编码
	public static final String CHARSET_NAME = "UTF-8";
	// 协议自定义对象包路径
	public static final String REQUEST_COMMON = "com.anlong.msgserver.entity.common";
	// 协议请求参数包路径
	public static final String REQUEST_PACKEAGE = "com.anlong.msgserver.entity.request";
	// 协议响应参数包路径
	public static final String RESPONSE_PACKEAGE = "com.anlong.msgserver.entity.response";
	// 请求对象类前缀
	public static final String REQUEST_PREFIX = "Request";
	// 配置业务前缀
	public static final String SPRING_BUSINESS_PREFIX = "biz";
	// 讨论组过期天数
	public static final Integer TALK_FINISH_DAY = 10;

	/** IoSession用户编号 */
	public static final String SESSION_USER_ID = "userId";
	/** IoSession登录类型 */
	public static final String SESSION_LOGIN_TYPE = "loginType";

	/** 用户类型－正式员工 */
	public static final Byte USER_TYPE_NORMAL = 1;
	/** 用户类型－外部用户 */
	public static final Byte USER_TYPE_OUT_WORKER = 2;
	/** 用户类型－系统管理员 */
	public static final Byte USER_TYPE_SYSTEM_MANAGER = 3;
	/** 用户类型－外部系统后台登录用户（如OA）； */
	public static final Byte USER_TYPE_OA_MANAGER = 4;

	/** 用户登录状态 0 不允许多端登录 */
	public static final Byte LOGINSTATE_NOT_ALLOW = 0;
	/** 用户登录状态 1 允许多端登录 */
	public static final Byte LOGINSTATE_ALLOW = 1;

	/** 用户登录方式 IM客户端 */
	public static final Byte LOGINTYPE_CLIENT = 1;
	/** 用户登录方式 WEB网页 */
	public static final Byte LOGINTYPE_WEB = 2;
	/** 用户登录方式 手机WAP */
	public static final Byte LOGINTYPE_WAP = 3;
	/** 用户登录方式 苹果手机 */
	public static final Byte LOGINTYPE_IPHONE = 4;
	/** 用户登录方式 安卓手机 */
	public static final Byte LOGINTYPE_ANDROID = 5;
	/** 用户登录方式 短信上行 */
	public static final Byte LOGINTYPE_UP_MSG = 81;
	/** 用户登录方式 移动OA */
	public static final Byte LOGINTYPE_OA = 91;
	/** 用户登录方式 管理中心 */
	public static final Byte LOGINTYPE_ADMINCENTER = 99;

	/** 用户退出状态 */
	public static final Byte ONLINESTATE_OFF = 0;
	/** 用户在线状态 */
	public static final Byte ONLINESTATE_ONLINE = 1;
	/** 用户忙碌状态 */
	public static final Byte ONLINESTATE_BUSY = 2;
	/** 用户离开状态 */
	public static final Byte ONLINESTATE_LEAVE = 3;
	/** 用户隐身状态 */
	public static final Byte ONLINESTATE_HIDDEN = 4;

	/** 超时检测时间间隔 */
	public static final Long CHECK_TIMEOUT_INTERVAL = 60 * 1000L;
	/** 离线超时 间隔 */
	public static final Long CHECK_TIMEOUT_CLIENT = 3 * 60 * 1000L;
	/** 移除离线人员时间 间隔 */
	public static final Long REMOVE_TIMEOUT_CLIENT = 30 * 60 * 1000L;

	/** 消息发送类型 个人 */
	public static final byte MSG_SENDTYPE_USER = 1;
	/** 消息发送类型 机构 */
	public static final byte MSG_SENDTYPE_DEP = 2;
	/** 消息发送类型 组群 */
	public static final byte MSG_SENDTYPE_GROUP = 3;
	/** 消息发送类型 快捷 */
	public static final byte MSG_SENDTYPE_TEMP = 4;
	/** 消息发送类型 系统消息 */
	public static final byte MSG_SENDTYPE_SYS_MSG = 8;
	/** 消息发送类型 系统广播 */
	public static final byte MSG_SENDTYPE_SYSTEM = 9;

	/** 消息类型 互动 */
	public static final byte MSG_MSGTYPE_WORDS = 1;
	/** 消息类型 文件 */
	public static final byte MSG_MSGTYPE_FILE = 2;
	/** 消息类型 应用消息 */
	public static final byte MSG_MSGTYPE_APP = 3;
	/** 消息类型 提醒消息； */
	public static final byte MSG_MSGTYPE_REMIND = 4;
	/** 消息类型 震动消息 */
	public static final byte MSG_MSGTYPE_VIBRATION = 5;
	/** 消息类型 组群邀请消息 */
	public static final byte MSG_MSGTYPE_GROUP_INVITE = 6;
	/** 消息类型 音频消息 */
	public static final byte MSG_MSGTYPE_AUDIO = 7;
	/** 消息类型 定位消息 */
	public static final byte MSG_MSGTYPE_LOCATION = 8;

	/** 消息接收类型 客户端接收 */
	public static final byte MSG_SENDMODE_CLIENT = 1;
	/** 消息接收类型 离线发短信 */
	public static final byte MSG_SENDMODE_OFFLINE_SMS = 2;
	/** 消息接收类型 发短信 */
	public static final byte MSG_SENDMODE_SMS = 3;
	/** 消息接收类型 广播发短信 */
	public static final byte MSG_SENDMODE_SMS2 = 4;

	/**
	 * 消息类型为1时： 1－发送成功； 2－发送失败； 3－已发送； 业务类型为2时： 1－文件待接收； 2－文件已接收； 业务类型为3时：
	 * 1－OA待处理信息； 2－OA已处理信息；
	 */
	public static final byte MSG_SEND_STATE_1 = 1;
	/** 发送状态 */
	public static final byte MSG_SEND_STATE_2 = 2;
	/** 发送状态 */
	public static final byte MSG_SEND_STATE_3 = 3;

	/** 群消息接收 */
	public static final byte GROUP_MSG_RECEIVE = 0;
	/** 群消息拒绝 */
	public static final byte GROUP_MSG_REJECT = 1;

	/** 组群消息接收状态 0为同意接收 */
	public static final byte GROUP_RECEIVE_FLAG_Y = 0;
	/** 组群消息接收状态 1为拒绝接收 */
	public static final byte GROUP_RECEIVE_FLAG_N = 1;
	/** 组群成员状态 0 正式成员 */
	public static final byte GROUPMEMBER_STATE_Y = 0;
	/** 组群成员状态 1 非正式成员,待确认 */
	public static final byte GROUPMEMBER_STATE_N = 1;

	/** 网关请求提交方式 1单号码请求 */
	public static final byte SMS_SUBTYPE_ALONE = 1;
	/** 网关请求提交方式 2多号码请求 */
	public static final byte SMS_SUBTYPE_MORE = 2;
	/** 网关请求提交方式 3文件方式请求 */
	public static final byte SMS_SUBTYPE_FILE = 3;

	/** 网关发送类型 单号码发送 */
	public static final String MT_COMMAND_ONE = "MT_REQUEST";
	/** 网关发送类型 多号码发送 */
	public static final String MT_COMMAND_MORE = "MULTI_MT_REQUEST";
	/** 网关发送类型 文件批量发送 */
	public static final String MT_COMMAND_FILE = "BATCH_MT_REQUEST";
	/** 网关发送 文件发送存放地址 */
	public static final String SMS_FILE_PATH = "/SmsFile/";

	/** 子号类型 1 单人 */
	public static final byte SUBNO_USER_TYPE_USER = 1;
	/** 子号类型 2 群组 */
	public static final byte SUBNO_USER_TYPE_GROUP = 2;

	/** 网关请求方式 1 尾号 */
	public static final byte SUBNO_CMD_TYPE_NUMBER = 1;
	/** 网关请求方式 2 指令 */
	public static final byte SUBNO_CMD_TYPE_INSTRUCTIONS = 2;

	/** 企业快快：1000-1400 */
	public static final String IM_MENUCODE = "1000-1400";
	/** 网关请求方式 */
	public static final byte WEBGATE_TYPE = Byte.parseByte(SystemGlobals.getValue("anlong.webgate.type"));
	/** 网关请求方式 1请求网关 */
	public static final byte WEBGATE_TYPE_WBS = 1;
	/** 网关请求方式 2请求平台 */
	public static final byte WEBGATE_TYPE_MONGATE = 2;
	/** 网关请求IP */
	public static final String anlong_WEBGATE_IP = SystemGlobals.getValue("anlong.webgate.ip");
	/** 网关请求端口 */
	public static final String anlong_WEBGATE_PORT = SystemGlobals.getValue("anlong.webgate.port");
	/** 网关请求总端口 */
	public static final String anlong_WEBGATE_ADDR = "http://" + anlong_WEBGATE_IP + ":" + anlong_WEBGATE_PORT + "/sms/mt";
	/** 文件服务器请求根地址 */
	public static final String anlong_FILE_SERVER_PATH = SystemGlobals.getValue("anlong.file.server");
	/** 语音服务器请求根地址 */
	public static final String anlong_VOICE_SERVER_PATH = SystemGlobals.getValue("anlong.voice.server");
	/** 本地文件请求地址 */
	public static final String anlong_LOCAL_PATH = SystemGlobals.getValue("anlong.local.path");
	/** IOS证书密码 */
	public static final String SECURITY_IOS_PASSOWRD = "kuaikuai";
	/** IOS证书文件 */
	public static final String SECURITY_IOS_FILE = "/WEB-INF/classes/aps_production.p12";// 正式环境
	// public static final String SECURITY_IOS_FILE =
	// "/WEB-INF/classes/aps_developer_identity.p12";

	/** 单号码发送 */
	public static final String MT_COMMAND_1 = "MT_REQUEST";
	/** 多号码发送 */
	public static final String MT_COMMAND_2 = "MULTI_MT_REQUEST";
	/** 文件批量发送 */
	public static final String MT_COMMAND_3 = "BATCH_MT_REQUEST";

	/** 消息未读 */
	public static final int MSG_HAS_NOT_READ = 0;
	/** 消息已读 */
	public static final int MSG_HAS_READ = 1;

	/** 文件保存绝对路径 */
	public static final String FILE_SAVE_PATH = SystemGlobals.getValue("anlong.im.file.path");
	/** 图片类型 聊天图片 */
	public static final byte IMAGE_TIPE_MESSAGE = 1;
	/** 图片类型 个人头像 */
	public static final byte IMAGE_TIPE_HEAD = 2;
	/** 图片类型 群组头像 */
	public static final byte IMAGE_TIPE_GROUP = 3;
	/** 音频类型 个人 */
	public static final byte AUDIO_TIPE_PERSONAL = 1;
	/** 音频类型 群组 */
	public static final byte AUDIO_TIPE_GROUP = 2;
	/** 个人头像保存相对路径 */
	public static final String FILE_HEAD_IMGAGE_PATH = "/image/headImg/";
	/** 群组头像保存相对路径 */
	public static final String FILE_GROUP_IMAGE_PATH = "/image/groupImg";
	/** 原始图片保存相当路径 */
	public static final String FILE_IMAGE_ORIGINAL_PATH = "/image/MsgImage/original/";
	/** 缩略图片保存相当路径 */
	public static final String FILE_IMAGE_SMALL_PATH = "/image/MsgImage/small/";
	/** 个人音频保存相对路径 */
	public static final String PERSONAL_AUDIO_PATH = "/audio/personal/";
	/** 群组音频保存相对路径 */
	public static final String GROUP_AUDIO_PATH = "/audio/group/";
	/** 缩略图片保存类型 */
	public static final String FILE_IMAGE_SMALL_SUFFIX = ".jpg";

	/** 发送文件socket触发空闲状态时间（秒） */
	public static final int FILE_IDLE_TIME = 60;
	/** 每次读取文件流大小 */
	public static final int FILE_BUFFER_SIZE = 1024 * 2;
	/** 每次写入文件流大小 */
	public static final int FILE_WRITE_SIZE = 1024 * 1024;
	/** 收到多大返回一次消息告诉客户端 */
	public static final int FILE_SEND_SIZE = 1024 * 1024;
	/** 等待接收线程休眠时间 */
	public static final int FILE_WAIT_MILLIS = 1000;
	/** 等级接收线程最大次数 */
	public static final int FILE_WAIT_COUNT = 10;
	/** 文件存放天数 */
	public static final int FILE_DAY_LIMIT = 15;
	/** 文件传输成功 */
	public static final int FILE_TRANSFER_SUCCEED = 1;
	/** 文件传输失败 */
	public static final int FILE_TRANSFER_FAIL = 2;
	/** 文件传输取消 */
	public static final int FILE_TRANSFER_CANCEL = 3;
	/** 文件传输模式 在线传输 */
	public static final int FILE_MODE_ONLINE = 1;
	/** 文件传输模式 离线传输 */
	public static final int FILE_MODE_OFFLINE = 2;
	/** 文件传输模式 点对点传输 */
	public static final int FILE_MODE_P2P = 3;
	/** 计费模式 预付费 */
	public static final int CHARGE_TYPE_PREPAID = 0;
	/** 计费模式 后付费 */
	public static final int CHARGE_TYPE_AFTER_PREPAID = 1;
	/** 计费模式 扣取上级部门费用 */
	public static final int CHARGE_TYPE_HIGHER_LEVEL = -1;

	public static final byte NUMBER_BYTE_0 = 0;
	public static final byte NUMBER_BYTE_1 = 1;
	public static final byte NUMBER_BYTE_2 = 2;
	public static final byte NUMBER_BYTE_3 = 3;
	public static final byte NUMBER_BYTE_4 = 4;
	
	public static final int NUMBER_INT_0 = 0;
	public static final int NUMBER_INT_1 = 1;
	public static final int NUMBER_INT_2 = 2;
	public static final int NUMBER_INT_3 = 3;
	public static final int NUMBER_INT_4 = 4;

}
