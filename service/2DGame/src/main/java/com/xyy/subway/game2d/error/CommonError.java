package com.xyy.subway.game2d.error;

/**
 * @author     ：xyy
 * @date       ：Created in 2019/08/23 12:45:23
 * @description：CommonError
 * @version:     1.0.0
 */
public interface CommonError {
    public int getErrorCode();
    public String getErrorMsg();
    public CommonError setErrMsg(String errMsg);
}
