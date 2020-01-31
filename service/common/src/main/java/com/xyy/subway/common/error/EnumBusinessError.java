package com.xyy.subway.common.error;

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
    NEW_USER(20002, "新用户，请先填写基本信息"),
    ALREADY_YOUR_FRIENDS_OR_WAIT(20003, "该用户已经是您的好友或好友申请正在等待通过"),
    NOT_YOUR_FRIEND(20004, "该用户还不是你的好友"),
    NOT_OUR_USER(20005, "该用户不是我们的用户"),

    // 30000开头为行程信息相关错误定义
    SCHEDULE_NOT_EXIST(30001, "行程不存在"),
    CITY_NOT_EXIST(30002, "城市不存在"),
    ROUTE_NOT_EXIST(30003, "路线不存在"),
    STATION_NOT_EXIST(30004, "地铁站不存在"),
    TRAIN_SCHEDULE_NOT_EXIST(30005, "车次不存在"),
    FAILED_TO_CREATE_SCHEDULE(30006, "行程创建失败"),
    FAILED_TO_UPDATE_SCHEDULE(30007, "行程更新失败"),
    START_STATION_NOT_EXIST(30008, "起始地铁站不存在"),
    END_STATION_NOT_EXIST(30009, "目标地铁站不存在");


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
