package com.xyy.subway.trueword.error;

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

    // 70000开头为数据库执行操作相关错误定义
    UPDATE_FAILED(70001, "更新操作失败"),
    FIND_FAILED(70002, "查询不存在的记录"),
    DELETE_FAILED(70003, "删除不存在的记录"),
    ADD_FAILED(70004, "添加有误，请检查输入"),
    RECORD_NOT_EXIST(70005, "记录不存在，请检查输入"),
    COURSE_NOT_EXIST(30006, "课程不存在，请检查输入"),
    TEACHER_NOT_EXIST(30008, "教师不存在，请检查输入"),
    STUDENT_NOT_EXIST(300009, "学生不存在，请检查输入"),

    /**
     *@author John STRAT
     *@date 2019/12/5 21:45
     */

    // 30000开头的为练习集相关信息
    PROBLEM_SET_NOT_EXIST(30001,"练习集不存在"),
    PROBLEM_SET_CHANGE_FAILED(30002,"练习集修改失败"),
    PROBLEM_SET_CHANGE_FAILED_CAUSE_BY_INVAILED_INPUT(30003,"输入参数错误"),
    PROBLEM_SET_DELETE_FAILED(30004,"练习集删除失败"),

    // 40000开头的为题目相关信息;
    PROBLEM_NOT_EXIST(40000,"暂无练习题"),

    // 50000开头的为试卷相关信息
    PAPER_NOT_EXIST(50000,"暂无练习卷"),
    PAPER_CREATE_FAILED(50001,"练习卷创建失败"),
    PAPER_DELETE_FAILED(50002,"练习卷删除失败"),
    PAPER_UPDATE_FAILED(50003,"练习卷修改失败"),

    // 60000开头的为试题列表相关信息
    PAPER_PROBLEM_CREATE_FAILED(60001,"试题添加失败"),
    PAPER_NOT_HAVE_PROBLEM(60002,"试卷内暂无试题"),
    PAPER_CHANGE_PROBLEM_FAILED(60003,"试卷更改试题失败");

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
