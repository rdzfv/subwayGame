package com.xyy.subway.game2d.controller;


import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xyy
 * @date 2020/1/22 19:17
*/
public class BaseController {
    // 定义exceptionhandler解决未被controller层吸收的exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        Map<String,Object> responseData = new HashMap<>();
        CommonReturnType commonReturnType = new CommonReturnType();
        if (ex instanceof BusinessException) {
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode",businessException.getErrorCode());
            responseData.put("errMsg",businessException.getErrorMsg());
        } else {
//            responseData.put("errCode", EnumBusinessError.UNKNOWN_ERROR.getErrorCode());
//            responseData.put("errMsg",EnumBusinessError.UNKNOWN_ERROR.getErrorMsg());
            System.out.println(ex);
        }
        commonReturnType.setStatus("fail");
        commonReturnType.setData(responseData);
        return commonReturnType;
    }
}
