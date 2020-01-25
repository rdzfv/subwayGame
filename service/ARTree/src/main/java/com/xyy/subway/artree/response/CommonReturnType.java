package com.xyy.subway.artree.response;

/**
 * @author     ：xyy
 * @date       ：Created in 2019/08/23 12:45:23
 * @description：CommonError
 * @version:     1.0.0
 */
public class CommonReturnType {
    //表明对应请求的返回处理结果室"success"或"false"
    private String status;
    private Object data;

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }
    public static CommonReturnType create(Object result,String status) {
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
