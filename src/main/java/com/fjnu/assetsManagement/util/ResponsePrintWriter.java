package com.fjnu.assetsManagement.util;

import com.alibaba.fastjson.JSONObject;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.entity.ResponseHead;
import com.fjnu.assetsManagement.enums.ResponseHeadEnum;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * Created by czx on 2018/10/8.
 */
public class ResponsePrintWriter {

//    private static DataCenterService dataCenterService;


    public static void setResponseValueAndReturn(HttpServletResponse response, ResponseHeadEnum responseHeadEnum) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            PrintWriter out = response.getWriter();

//        if (dataCenterService == null)
//            dataCenterService = SpringApplicationContextUtil.getBean(DataCenterService.class);

            ResponseHead head = new ResponseHead();
//                head.setFunctionNo(dataCenterService.getParamValueFromHeadOfRequestParamJsonByParamName("functionNo"));
            head.setCode(responseHeadEnum.getCode());
            head.setZh_msg(responseHeadEnum.getZh_msg());
            head.setEn_msg(responseHeadEnum.getEn_msg());


            ResponseData responseData = new ResponseData();
            responseData.setHead(head);
            JSONObject result = (JSONObject) JSONObject.toJSON(responseData);
            out.println(result);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
