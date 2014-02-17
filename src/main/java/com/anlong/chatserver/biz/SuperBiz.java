package com.anlong.chatserver.biz;

import com.anlong.chatserver.entity.common.OnlineUser;
import com.anlong.chatserver.entity.request.BaseRequest;

public interface SuperBiz {
	/** biz实现类必须重写该方法 */
	public void execute(BaseRequest baseRequest, OnlineUser onlineUser);

}
