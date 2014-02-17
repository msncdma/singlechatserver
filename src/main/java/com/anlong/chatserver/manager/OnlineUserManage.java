package com.anlong.chatserver.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.anlong.chatserver.constant.CodeConstants;
import com.anlong.chatserver.constant.StaticValue;
import com.anlong.chatserver.entity.common.OnlineUser;
import com.anlong.chatserver.entity.response.Response1020;
import com.anlong.chatserver.util.Utils;


/**
 * @Title: OnlineUserManage.java 
 * @Package com.anlong.chatserver.manager
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午5:40:36 
 * @version V1.0   
 * @Description: TODO
 */
public class OnlineUserManage {
	Logger logger = Logger.getLogger(OnlineUserManage.class);
	private static OnlineUserManage manage;
	// Map<CorpCode, Map<UserId, Map<LoginType, OnlineUser>>>
	private Map<String, Set<Integer>> corpUserMap = new HashMap<String, Set<Integer>>();
	private Map<Integer, Map<Byte, OnlineUser>> onlineUserMap = new HashMap<Integer, Map<Byte, OnlineUser>>();
	private Map<Integer, Map<Byte, OnlineUser>> offlineUserMap = new HashMap<Integer, Map<Byte, OnlineUser>>();
	private Map<Integer, OnlineUser> managerMap = new HashMap<Integer, OnlineUser>();;

	public static OnlineUserManage getManage() {
		if (manage == null) {
			manage = new OnlineUserManage();
		}
		return manage;
	}

	private OnlineUserManage() {
		// 清空原在线用户表中数据,将数据移到用户登录历史纪录表
		// this.deleteTableLfOnlineUserByAll();
		// 开启线程,定时清理登录超时用户
		new Thread() {
			public void run() {
				final Long sleepTime = StaticValue.CHECK_TIMEOUT_INTERVAL;
				while (true) {
					try {
						OnlineUserManage.getManage().removeTimeOutUser();
						Thread.sleep(sleepTime);
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			};
		}.start();
	}

	/**
	 * 添加在线用户
	 * 
	 * @param onlineUser
	 */
	public synchronized void addOnlineUser(OnlineUser onlineUser) {
		IoSession session = onlineUser.getSession();
		session.setAttribute(StaticValue.SESSION_USER_ID, onlineUser.getUserId());
		session.setAttribute(StaticValue.SESSION_LOGIN_TYPE, onlineUser.getLoginType());
		if (this.isUserOnlineExist(onlineUser.getUserId())) { // 用户在线, 仅添加在线队列
			this.getOnlineUser(onlineUser.getUserId()).put(onlineUser.getLoginType(), onlineUser);
		} else if (corpUserMap.containsKey(onlineUser.getCorpCode())) { // 用户不在线,但企业map中存在该企业数据,
																		// 添加新登录用户信息
			corpUserMap.get(onlineUser.getCorpCode()).add(onlineUser.getUserId());
			Map<Byte, OnlineUser> map = new HashMap<Byte, OnlineUser>();
			map.put(onlineUser.getLoginType(), onlineUser);
		} else { // 内存中添加企业及用户信息
			Set<Integer> set = new HashSet<Integer>();
			set.add(onlineUser.getUserId());
			corpUserMap.put(onlineUser.getCorpCode(), set);
			Map<Byte, OnlineUser> map = new HashMap<Byte, OnlineUser>();
			map.put(onlineUser.getLoginType(), onlineUser);
			onlineUserMap.put(onlineUser.getUserId(), map);
		}
		System.out.println("***********当前" + onlineUserMap.size() + "人在线***********");
	}

	/**
	 * 移除在线用户信息
	 * 
	 * @param userId
	 * @param loginType
	 */
	public synchronized void deleteOnlineUser(int userId, byte loginType) {
		Map<Byte, OnlineUser> map = this.getOnlineUser(userId);
		// this.updateOnlineState(userId, loginType,
		// StaticValue.ONLINESTATE_OFF);
		if (Utils.isNotNull(map) && !map.isEmpty() && map.containsKey(loginType)) {
			OnlineUser user = map.get(loginType);
			if (map.size() > 1) { // 多种登录方式, 仅移除其一
				map.remove(loginType);
			} else {
				corpUserMap.get(map.get(loginType).getCorpCode()).remove(userId);
				onlineUserMap.remove(userId);
			}
			// sendOnOffMsgBy1020(user, userId, (byte)0, loginType,
			// CodeConstants.RTCODE_103);
		}
	}

	/**
	 * 将在线队列中用户移到离线队列中
	 * 
	 * @param userId
	 * @param loginType
	 */
	public void moveOnlineuserToOfflineUserMap(int userId, byte loginType) {
		OnlineUser user = this.getOnlineUser(userId, loginType);
		if (Utils.isNotNull(user)) {
			this.deleteOnlineUser(userId, loginType);
			// user.setOnlineState(StaticValue.ONLINESTATE_OFF);
			this.addOfflineUser(user);
		}
	}

	/**
	 * 将离线队列中用户移到在线队列中
	 * 
	 * @param userId
	 * @param loginType
	 */
	public void moveOfflineUserToOnlineuserMap(int userId, byte loginType, IoSession session) {
		if (offlineUserMap.containsKey(userId)) {
			OnlineUser user = offlineUserMap.get(userId).get(loginType);
			if (Utils.isNotNull(user)) {
				user.setSession(session);
				this.deleteOfflineUser(userId, loginType);
				user.setLoginTime(new Date());
				this.addOnlineUser(user);
			}
		}
	}

	/**
	 * 添加离线用户
	 * 
	 * @param lfOnlineUser
	 */
	private synchronized void addOfflineUser(OnlineUser offlineUser) {
		if (offlineUserMap.containsKey(offlineUser.getUserId())) {
			offlineUserMap.get(offlineUser.getUserId()).put(offlineUser.getLoginType(), offlineUser);
		} else {
			Map<Byte, OnlineUser> map = new HashMap<Byte, OnlineUser>();
			map.put(offlineUser.getLoginType(), offlineUser);
			offlineUserMap.put(offlineUser.getUserId(), map);
		}
	}

	/**
	 * 移除离线用户信息
	 * 
	 * @param userId
	 * @param loginType
	 */
	private synchronized void deleteOfflineUser(int userId, byte loginType) {
		if (Utils.isNull(userId) || Utils.isNull(loginType))
			return;
		Map<Byte, OnlineUser> map = offlineUserMap.get(userId);
		if (Utils.isNotNull(map) && !map.isEmpty() && map.containsKey(loginType)) {
			if (map.size() > 1) {
				map.remove(loginType);
			} else {
				offlineUserMap.remove(userId);
			}
		}
	}

	/**
	 * 修改用户在线状态
	 * 
	 * @param userId
	 * @param corpCode
	 * @param state
	 */
	public boolean updateOnlineState(int userId, Byte loginType, Byte onlineState) {
		Map<Byte, OnlineUser> map = this.getOnlineUser(userId);
		try {
			if (Utils.isNotNull(map) && !map.isEmpty() && map.containsKey(loginType)) {
				OnlineUser onlineUser = map.get(loginType);
				if (onlineState == StaticValue.ONLINESTATE_OFF) {
					deleteOnlineUser(userId, loginType);
					sendOnOffMsgBy1020(onlineUser, userId, (byte) 0, loginType, CodeConstants.RTCODE_0);
				} else {
					onlineUser.setOnlineState(onlineState);
				}
				// 推送1020报文给所有在线用户
				List<OnlineUser> onlineUserList = this.getAllOnlineUser(onlineUser.getCorpCode());
				Byte priorityLoginType = this.getPriorityOnlineState(userId);// 获取用户最高优先级在线方式
				Byte priorityOnlineState = onlineState;
				if (Utils.equals(priorityLoginType, 0)) {
					priorityOnlineState = 0;
				} else {
					priorityOnlineState = 1;
				}
				for (Iterator<OnlineUser> iterator = onlineUserList.iterator(); iterator.hasNext();) {
					OnlineUser user = iterator.next();
					if (!Utils.equals(user.getUserId(), userId)) {// 不给自己推送状态
						sendOnOffMsgBy1020(user, userId, priorityOnlineState, priorityLoginType, CodeConstants.RTCODE_0);
					} else {
						// 同一用户 其他在线端如何处理待定.
						if (!Utils.equals(user.getLoginType(), loginType)) {
							sendOnOffMsgBy1020(user, userId, onlineState, loginType, CodeConstants.RTCODE_0);
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("修改用户在线状态出现异常", e);
		}
		return false;
	}

	/**
	 * 根据userId获取在线用户信息
	 * 
	 * @param guid
	 * @return LfOnlineUser
	 */
	public Map<Byte, OnlineUser> getOnlineUser(int userId) {
		if (onlineUserMap.containsKey(userId)) {
			return onlineUserMap.get(userId);
		}
		return null;
	}

	/**
	 * 根据userId获取指定登录方式的在线用户信息
	 * 
	 * @param guid
	 * @return LfOnlineUser
	 */
	public OnlineUser getOnlineUser(int userId, Byte loginType) {
		if (onlineUserMap.containsKey(userId)) {
			return onlineUserMap.get(userId).get(loginType);
		}
		return null;
	}

	/** 判断用户是否在线 */
	public boolean isUserOnlineExist(int userId) {
		if (null == this.getOnlineUser(userId)) {
			return false;
		}
		return true;
	}

	/** 判断用户是否在线,需指定登录方式 */
	public boolean isUserOnlineExist(int userId, Byte loginType) {
		if (onlineUserMap.containsKey(userId) && Utils.isNotNull(onlineUserMap.get(userId).get(loginType))) {
			return true;
		}
		return false;
	}

	/** 判断用户是否在离线队列中,需指定登录方式 */
	public OnlineUser getOfflineUser(int userId, Byte loginType) {
		if (offlineUserMap.containsKey(userId)) {
			return offlineUserMap.get(userId).get(loginType);
		}
		return null;
	}

	/**
	 * 根据企业编码获取当前企业所有在线用户
	 * 
	 * @param corpCode
	 * @return List<LfOnlineUser>
	 */
	public List<OnlineUser> getAllOnlineUser(String corpCode) {
		List<OnlineUser> list = new ArrayList<OnlineUser>();
		Set<Integer> userSet = null;
		if (Utils.isNotNull(corpCode)) {
			userSet = this.corpUserMap.get(corpCode);
		} else {
			userSet = this.onlineUserMap.keySet();
		}
		if (Utils.isNotNull(userSet) && !userSet.isEmpty()) {
			for (Integer userId : userSet) {
				Map<Byte, OnlineUser> map = this.getOnlineUser(userId);
				list.addAll(map.values());
			}
		}
		return list;
	}

	/** 获取内存中当前在线人数 */
	public int getOnlineUserCount(String corpCode) {
		int count = 0;
		if (corpUserMap.containsKey(corpCode)) {
			count = corpUserMap.get(corpCode).size();
		}
		return count;
	}

	/**
	 * 修改内存中用户最后活动时间
	 * 
	 * @param guid
	 * @param corpCode
	 */
	public void updateLastActivTime(int userId, Byte loginType) {
		Map<Byte, OnlineUser> map = this.getOnlineUser(userId);
		if (Utils.isNotNull(onlineUserMap) && map != null && !map.isEmpty()) {
			OnlineUser onlineUser = map.get(loginType);
			if (Utils.isNotNull(onlineUser)) {
				onlineUser.setLastActiveTime(System.currentTimeMillis());
			}
		}
	}

	/**
	 * 移除超时用户
	 */
	private void removeTimeOutUser() {
		Long nowTime = System.currentTimeMillis();
		/** 删除超时用户 */
		List<OnlineUser> list = this.getAllOnlineUser(null);
		for (OnlineUser onlineUser : list) {
			if (nowTime - onlineUser.getLastActiveTime() > StaticValue.CHECK_TIMEOUT_CLIENT) {
				this.updateOnlineState(onlineUser.getUserId(), onlineUser.getOnlineState(), StaticValue.ONLINESTATE_OFF);
				this.moveOnlineuserToOfflineUserMap(onlineUser.getUserId(), onlineUser.getLoginType());
			}
		}
	}


	/**
	 * 推送状态改变
	 * 
	 * @param onlineUser
	 * @param operateType
	 */
	public void sendOnOffMsgBy1020(OnlineUser onlineUser, Integer userId, Byte onlineState, Byte loginType, Short rtCode) {
		IoSession session = onlineUser.getSession();
		if (Utils.isNotNull(session) && session.isConnected()) {
			Response1020 response = new Response1020();
			response.setbCode(CodeConstants.BCODE_1020);
			response.setKey(onlineUser.getKey());
			response.setUid(onlineUser.getUserId());
			response.setRtCode(rtCode);
			response.setUserId(userId);
			response.setOnlineState(onlineState);
			response.setLoginType(loginType);
			session.write(response);
		}
	}

	/**
	 * 管理员在线列表
	 * 
	 * @param onlineUser
	 * @return boolean
	 */
	public boolean addManageUser(OnlineUser onlineUser) {
		if (Utils.equals(onlineUser.getUserType(), StaticValue.USER_TYPE_OA_MANAGER)) {
			managerMap.put(onlineUser.getUserId(), onlineUser);
			logger.info("管理员" + onlineUser.getName() + "登录==" + managerMap.toString());
			return true;
		}
		return false;
	}

	/**
	 * 判断OA用户是否在线
	 * 
	 * @param uid
	 * @param token
	 * @return boolean
	 */
	public boolean isManagerOnlineExist(Integer userId, String token) {
		if (!managerMap.isEmpty() && managerMap.get(userId).getKey().equals(token)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据guid获取OA管理员用户
	 * 
	 * @param uid
	 * @return
	 */
	public OnlineUser getOaOnlineUserByGuid(Integer userId) {
		if (Utils.isNotNull(userId) && !managerMap.isEmpty() && managerMap.containsKey(userId)) {
			return managerMap.get(userId);
//			Set<String> keyArr = managerMap.keySet();
//			for (String key : keyArr) {
//				OnlineUser onlineUser = managerMap.get(key);
//				if (Utils.isNull(onlineUser)) {
//					continue;
//				}
//				if (Utils.equals(onlineUser.getUserId(), userId)) {
//					return onlineUser;
//				}
//			}
		}
		return null;
	}

	/**
	 * 获取用户最高优先级的在线方式, 用于推送在线状态
	 * 
	 * @return
	 */
	public Byte getPriorityOnlineState(Integer userId) {
		Map<Byte, OnlineUser> onlineMap = this.getOnlineUser(userId);
		if (Utils.isNotNull(onlineMap) && !onlineMap.isEmpty()) {
			Set<Byte> set = onlineMap.keySet();
			Byte state = 80;
			for (Byte loginType : set) {
				if (loginType < state) {
					state = loginType;
				}
			}
			return state;
		}
		return 0;
	}

	

	public List<Object> getOnlineCorpList() {
		return Arrays.asList(this.corpUserMap.keySet().toArray());
	}

	
}