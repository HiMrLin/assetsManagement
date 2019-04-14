package com.fjnu.assetsManagement.util;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName:AjaxResponse
 * @Description:
 * @Author:lwn
 * @Date:2019/3/31 17:03
 **/
public class AjaxResponse {
    public static void ajaxPrintByJson(Object content) {
        HttpServletResponse response = responseCommon();
        try {
            response.setContentType("text/json;charset=UTF-8");
            java.io.PrintWriter out = response.getWriter();
            out.print(content);
            out.flush();//这里写的不严谨
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static HttpServletResponse responseCommon(){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        return response;
    }
}
