package com.fjnu.assetsManagement.exception;


import com.fjnu.assetsManagement.enums.ResponseHeadEnum;
import com.fjnu.assetsManagement.util.ResponsePrintWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 处理因客户端传递的格式有误引起的异常
    @ExceptionHandler(value = Exception.class)
    public void defaultExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {

        String exceptionName = e.getClass().getSimpleName();
        log.debug("->Global exception -> exceptionName is:" + exceptionName);
        log.debug(e.getMessage());
        e.printStackTrace();


        switch (exceptionName) {

            //方法上加了注解权限被拒绝访问
            case "AccessDeniedException":
                ResponsePrintWriter.setResponseValueAndReturn(response, ResponseHeadEnum.NO_OPERATING_RIGHTS);
                break;

            default:
                ResponsePrintWriter.setResponseValueAndReturn(response, ResponseHeadEnum.FAILURE);
                break;
        }

    }

}
