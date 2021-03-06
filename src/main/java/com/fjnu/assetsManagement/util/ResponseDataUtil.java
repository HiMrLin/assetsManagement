package com.fjnu.assetsManagement.util;

import com.alibaba.fastjson.JSONObject;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.entity.ResponseHead;
import com.fjnu.assetsManagement.enums.IReasonOfFailure;
import com.fjnu.assetsManagement.enums.ResponseHeadEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * author lwn
 * description
 * date
 * param
 * return
 */
public class ResponseDataUtil {

	public static void setResponseDataWithFailureInfo(ResponseData responseData,IReasonOfFailure reasonOfFailure) {
		ResponseHead head=responseData.getHead();
		head.setCode(ResponseHeadEnum.FAILURE.getCode());
		head.setEn_msg(reasonOfFailure.getEnMsgOfFailure());
		head.setZh_msg(reasonOfFailure.getZhMsgOfFailure());
	}

	public static ResponseData createResponseData(HttpServletRequest request, HttpServletResponse response) {
		ResponseData responseData=new ResponseData();
		String functionNo = HttpServletRequestUtil.getParamValueFromHeadByParamName(request, response, "functionNo");
		if (functionNo == null) {
			return null;
		}
		//String token = HttpServletRequestUtil.getParamValueFromHeadByParamName(request,"token");
		ResponseHead head=new ResponseHead();
		head.setFunctionNo(functionNo);
		//head.setToken(token);
		responseData.setHead(head);
		JSONObject data=new JSONObject();
		responseData.setHead(head);
		responseData.setData(data);
		return responseData;
	}

	public static void setHeadOfResponseDataWithSuccessInfo(ResponseData responseData) {
		ResponseHead head=responseData.getHead();
		head.setCode(ResponseHeadEnum.SUCCESS.getCode());
		head.setZh_msg(ResponseHeadEnum.SUCCESS.getZh_msg());
		head.setEn_msg(ResponseHeadEnum.SUCCESS.getEn_msg());
	}

	public static <T> void putValueToData(ResponseData responseData,String key, T value) {
		JSONObject data=responseData.getData();
		data.put(key, value);
		setHeadOfResponseDataWithSuccessInfo(responseData);
	}

}
