package com.fjnu.assetsManagement.action;

import com.alibaba.fastjson.JSONObject;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.entity.ResponseHead;
import com.fjnu.assetsManagement.enums.IReasonOfFailure;
import com.fjnu.assetsManagement.enums.ResponseHeadEnum;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.web.bind.annotation.CrossOrigin;

@ParentPackage("json-default")
@Results({
        @Result(name = "success",type="json", params={"root","responseData"}),
        @Result(name = "error", type = "json", params = {"root", "responseData"}),
        @Result(name = "none", type = "json", params = {"root", "responseData"})
})
@CrossOrigin
public class JsonAction {
    protected ResponseData responseData = new ResponseData();
    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public ResponseData getResponseData() {
        return responseData;
    }
    //构造失败信息
    public  void setResponseDataWithFailureInfo(ResponseData responseData,IReasonOfFailure reasonOfFailure) {
        ResponseHead head=responseData.getHead();
        head.setCode(ResponseHeadEnum.FAILURE.getCode());
        head.setEn_msg(reasonOfFailure.getEnMsgOfFailure());
        head.setZh_msg(reasonOfFailure.getZhMsgOfFailure());
        this.responseData=responseData;
    }
    //构造操作成功信息
    public  void setHeadOfResponseDataWithSuccessInfo(ResponseData responseData) {
        ResponseHead head=responseData.getHead();
        head.setCode(ResponseHeadEnum.SUCCESS.getCode());
        head.setZh_msg(ResponseHeadEnum.SUCCESS.getZh_msg());
        head.setEn_msg(ResponseHeadEnum.SUCCESS.getEn_msg());
        this.responseData=responseData;
    }

    //将数据返回给前台
    public  <T> void putValueToData(ResponseData responseData,String key, T value) {
        //构造成功返回的信息头
        this.setHeadOfResponseDataWithSuccessInfo(responseData);
        //设置返回数据
        this.responseData=responseData;
        JSONObject data=responseData.getData();
        data.put(key, value);
    }
}
