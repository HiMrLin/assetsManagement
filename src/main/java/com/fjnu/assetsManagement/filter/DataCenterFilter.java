package com.fjnu.assetsManagement.filter;

import com.alibaba.fastjson.JSONObject;
import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.enums.ResponseHeadEnum;
import com.fjnu.assetsManagement.service.DataCenterService;
import com.fjnu.assetsManagement.util.ResponseDataUtil;
import com.fjnu.assetsManagement.util.ResponsePrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class DataCenterFilter implements Filter {
    @Autowired
    private DataCenterService dataCenterService;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("--------DataCenterFilter------doFilter---init---");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("--------DataCenterFilter------doFilter---start---");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();

        log.info("--------DataCenterFilter------doFilter---servletPath---{}", servletPath);


        JSONObject requestParamJson = null;
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null) {
            stringBuilder.append(inputStr);
        }

        String requestStr = stringBuilder.toString();
        try {
            requestParamJson = JSONObject.parseObject(requestStr);
        } catch (Exception e) {
            log.debug("xss ->reason:" + e.getMessage());
            ResponsePrintWriter.setResponseValueAndReturn(response, ResponseHeadEnum.ILLEGAL_ATTACK);
            return;
        }
        request.setAttribute("requestParamJson", requestParamJson);
        ResponseData responseData = ResponseDataUtil.createResponseData(request, response);

        if (dataCenterService == null) {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
            dataCenterService = webApplicationContext.getBean(DataCenterService.class);
        }
        log.info("dataCenter init");
        dataCenterService.init(requestParamJson, responseData);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
