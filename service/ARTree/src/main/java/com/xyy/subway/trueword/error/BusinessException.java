package com.xyy.subway.trueword.error;


/**
 * @author     ：xyy
 * @date       ：Created in 2019/08/23 12:45:23
 * @description：包装器业务异常类实现
 * @version:     1.0.0
 */
public class BusinessException extends Exception implements CommonError {
    private CommonError commonError;
    // 直接接受EmBusinessException的传参用于构造业务异常
    public BusinessException(CommonError commonError) {
        super();
        this.commonError=commonError;
    }
    // 接受自定义errorMsg的方式构造业务异常
    public BusinessException(CommonError commonError,String errMsg) {
        super();
        this.commonError=commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }
}
