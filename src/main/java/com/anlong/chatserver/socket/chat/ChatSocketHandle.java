package com.anlong.chatserver.socket.chat;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.anlong.chatserver.biz.SuperBiz;
import com.anlong.chatserver.constant.CodeConstants;
import com.anlong.chatserver.constant.StaticValue;
import com.anlong.chatserver.entity.common.OnlineUser;
import com.anlong.chatserver.entity.request.BaseRequest;
import com.anlong.chatserver.entity.response.BaseResponse;
import com.anlong.chatserver.entity.response.Response1010;
import com.anlong.chatserver.manager.OnlineUserManage;
import com.anlong.chatserver.util.SpringUtil;
import com.anlong.chatserver.util.Utils;


/**
 * @Title: ChatSocketHandle.java 
 * @Package com.anlong.chatserver.socket.chat
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午4:18:11 
 * @version V1.0   
 * @Description: TODO
 */
public class ChatSocketHandle extends IoHandlerAdapter {
	Logger logger = Logger.getLogger(ChatSocketHandle.class);
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("---出现错误---");
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		super.messageReceived(session, message);
		logger.info("接收到参数" + message.toString());
		if (message instanceof BaseRequest) {
			BaseRequest baseRequest = (BaseRequest) message;
			BaseResponse response = new Response1010();
			Utils.getResponse(baseRequest, response, CodeConstants.BCODE_1010);
			OnlineUserManage manage = OnlineUserManage.getManage();
			// 在线用户
			OnlineUser onlineUser = manage.getOnlineUser(baseRequest.getUid(),
					baseRequest.getApId());

			// 如果不是101登录请求或者104更改密码请求，则返回错误
			if (baseRequest.getbCode() == CodeConstants.BCODE_101
					|| baseRequest.getbCode() == CodeConstants.BCODE_104) {
				onlineUser = new OnlineUser();
				onlineUser.setSession(session);
			} else if (onlineUser == null) {
				// 如果在离线列表并且密钥正确,则激活在线
				OnlineUser offlineUser = manage.getOfflineUser(
						baseRequest.getUid(), baseRequest.getApId());
				if (offlineUser != null
						&& Utils.equals(offlineUser.getKey(),
								baseRequest.getKey())) {
					onlineUser = offlineUser;
					manage.moveOfflineUserToOnlineuserMap(baseRequest.getUid(),
							baseRequest.getApId(), session);
				} else {
					// 判断是否外部管理员帐号
					OnlineUser oaManager = manage
							.getOaOnlineUserByGuid(baseRequest.getUid());
					if (oaManager != null
							&& Utils.equals(oaManager.getKey(),
									baseRequest.getKey())) {
						onlineUser = oaManager;
					} else {
						response.setRtCode(CodeConstants.RTCODE_105);
					}
				}
			} else {
				// 用户密钥错误
				if (!Utils.equals(onlineUser.getKey(), baseRequest.getKey())) {
					response.setRtCode(CodeConstants.RTCODE_107);
				} else {
					onlineUser.setSession(session);
				}
			}

			// 存在错误码时,不进行逻辑处理, 直接返回错误
			if (Utils.equals(response.getRtCode(), CodeConstants.RTCODE_0)) {
				SuperBiz biz = (SuperBiz) SpringUtil
						.getBean(StaticValue.SPRING_BUSINESS_PREFIX
								+ baseRequest.getbCode());
				biz.execute(baseRequest, onlineUser);
			} else {
				System.out.println(response.toString());
				session.write(response);
			}
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		try {
			super.sessionClosed(session);
			session.close(true);
			// 移除在线列表到离线列表
			Integer userId = (Integer) session.getAttribute(StaticValue.SESSION_USER_ID);
			Byte loginType = (Byte) session.getAttribute(StaticValue.SESSION_LOGIN_TYPE);
			if (userId != null && loginType != null) {
				OnlineUser user = OnlineUserManage.getManage().getOnlineUser(userId, loginType);
				if (Utils.equals(session.getId(), user.getSession().getId())) {
					OnlineUserManage.getManage().updateOnlineState(userId, loginType, StaticValue.ONLINESTATE_OFF);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("---创建连接---"+session.getRemoteAddress()+"|||");
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		super.sessionOpened(session);
	}

}
