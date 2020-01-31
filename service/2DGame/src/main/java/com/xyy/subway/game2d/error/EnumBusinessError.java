package com.xyy.subway.game2d.error;

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
    USERINFO_UPDATE_FAILED(20002, "用户信息更新失败"),
    USER_NOT_ENOUGH_MONEY(20003, "账户资金不足"),
    USER_NOT_ENOUGH_WORKER(20003, "可用小工数不足"),

    // 30000 开头为虚拟站点相关错误定义
    VSTATION_NOT_EXIST(30001, "虚拟站点不存在"),


    // 40000 开头为虚拟路线的相关错误定义
    VROUTE_NOT_EXIST(40001, "虚拟路线不存在"),


    // 50000 开头为虚拟站点商店的相关错误定义
    VSTATIONSTORE_NOT_EXIST(50001, "虚拟站点商店不存在"),


    // 60000 开头为虚拟站点商店类型的相关错误定义
    VSTATIONSTORETYPE_NOT_EXIST(60001, "虚拟站点商店类型不存在"),


    // 70000 开头为虚拟站点队伍的相关错误定义
    VSTATIONTEAM_NOT_EXIST(70001, "虚拟站点队伍不存在"),


    // 80000 开头为虚拟站点队伍的相关错误定义
    VSTATIONTEAMTYPE_NOT_EXIST(80001, "虚拟站点队伍类型不存在");

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
