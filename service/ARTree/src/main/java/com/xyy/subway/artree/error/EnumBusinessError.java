package com.xyy.subway.artree.error;

/**
 * @author ：xyy
 * @date ：Created in 2019/08/23 12:45:23
 * @description：EnumBusinessError
 * @modified By：xyy in 2019/12/04 16:53:28
 * @version: 1.0.0
 */
public enum EnumBusinessError implements CommonError {
    // 00002 未知错误
    UNKNOWN_ERROR(10002, "未知错误"),


    // 10001 通用错误类型
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    ILLEGAL_REQUEST(10004, "不合法的请求，已拒绝"),


    // 20000 开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    NOT_ENOUGHT_WATER(20003, "水滴数目不足"),
    EXCEED_WATER_TO_SELF(20002, "今日对自己浇水已达上限"),
    NOT_HAVEA_TREE_YET(20004, "暂时还未拥有属于自己的树");




    private EnumBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;

    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
