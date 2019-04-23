package com.fjnu.assetsManagement.service;

import com.alibaba.fastjson.JSONObject;
import com.fjnu.assetsManagement.entity.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component

public class DataCenterService {
	private Logger logger= LoggerFactory.getLogger(getClass());

	private static final String APP_VER_NO="appVerNo";//app版本号
	private static final String FUNCTION_NO="functionNo";//功能号

	// 多线程并发的环境，必须使用ThreadLocal存放中间结果，
	// 大部分的中间结果，保存到data中，dataLocal存放当前线程的data，配合setData和getData来读写data中存放的数据
	private ThreadLocal<JSONObject> dataLocal = new ThreadLocal<>();

	/**
	 * 将数据写入到data中
	 * 
	 * @param dataName
	 *            数据的名称，将来要通过它来获取存入的值
	 * @param dataValue
	 *            数据的值
	 */
	public <T> void setData(String dataName, T dataValue) {
		JSONObject data = dataLocal.get();
		data.put(dataName, dataValue);
	}

	/**
	 * 将数据从data中读出
	 * 
	 * @param dataName
	 *            要读取数据的名称
	 * @return 返回数据的值
	 */
	@SuppressWarnings("unchecked")
	public <T> T getData(String dataName) {
		JSONObject data = dataLocal.get();
        if (data == null) {
            return null;
        }
		T tempData = (T) data.get(dataName);
		return tempData;
	}

	public void init(JSONObject requestParamJson, ResponseData responseData) {

		//Xss过滤
//		String str=requestParamJson.toString();
//		if (StringUtils.isNotBlank(str)) {
//			str = JsoupUtil.clean(str);
//		}
//		JSONObject cleanRequestParamJson=JSONObject.parseObject(str);

		JSONObject data = new JSONObject();
		this.dataLocal.set(data);
//		User currentLoginUser=getCurrentLoginUser();
//		this.setData("currentLoginUser", currentLoginUser);
		this.setData("requestParamJson", requestParamJson);
		this.setData("responseData", responseData);

	}

	public String getAppVersionNo() {
		return this.getParamValueFromHeadOfRequestParamJsonByParamName(APP_VER_NO);
	}


	public String getFunctionNo(){
		return this.getParamValueFromHeadOfRequestParamJsonByParamName(FUNCTION_NO);
	}


	public ResponseData getResponseDataFromDataLocal(){
	    return  this.getData("responseData");
    }

	public <T> T getParamValueFromParamOfRequestParamJsonByParamName(String paramName) {
		JSONObject data=dataLocal.get();
		JSONObject requestParamJson=data.getJSONObject("requestParamJson");
		JSONObject param=requestParamJson.getJSONObject("param");
		@SuppressWarnings("unchecked")
		T paramValue=(T) param.get(paramName);
		return paramValue;
	}
	public <T> T getParamValueFromHeadOfRequestParamJsonByParamName(String paramName) {
		JSONObject data=dataLocal.get();
        if (data == null) {
            return null;
        }
		JSONObject requestParamJson=data.getJSONObject("requestParamJson");
        if (requestParamJson == null) {
            return null;
        }
		JSONObject head=requestParamJson.getJSONObject("head");
		@SuppressWarnings("unchecked")
		T paramValue=(T) head.get(paramName);
		return paramValue;
	}

	public void remove() {
		this.dataLocal.remove();
	}

}
