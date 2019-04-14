package com.fjnu.assetsManagement.util;

import com.fjnu.assetsManagement.entity.ResponseHead;
import com.fjnu.assetsManagement.enums.ResponseHeadEnum;

public class ResponseHeadUtil {
	public static ResponseHead createResponseHead(ResponseHeadEnum responseHeadEnum) {
		ResponseHead responseHead = new ResponseHead();
		responseHead.setCode(responseHeadEnum.getCode());
		responseHead.setZh_msg(responseHeadEnum.getZh_msg());
		responseHead.setEn_msg(responseHeadEnum.getEn_msg());
		return responseHead;
	}

}
