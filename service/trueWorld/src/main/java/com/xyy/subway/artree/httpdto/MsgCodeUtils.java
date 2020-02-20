package com.xyy.subway.artree.httpdto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author     ：xyy
 * @date       ：Created in 2019/12/20
 * @description：
 * @version:     1.0.0
 */

public class MsgCodeUtils {

    public static final int SUCCESS        = 200;
    public static final int FAIL           = 400;
    public static final int NOT_AUTHORIZED = 401;
    public static final int NOT_FOUND      = 404;

    private static Map<String, String> resultCodeConf;

    static {
        resultCodeConf = Utils.load_conf("/msgcode.properties");
    }

    @Deprecated
    public static HashMap<String, Object> getMessageCode(int code) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", getCodeMessage(code));
        return result;
    }

    @Deprecated
    public static HashMap<String, Object> getMessageCode(int code, String message) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", message);
        return result;
    }

    @Deprecated
    public static HashMap<String, Object> getMessageCode(int code, Map<String, Object> map) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", getCodeMessage(code));
        if (map != null) {
            result.put("data", map);
        }
        return result;
    }

    @Deprecated
    public static HashMap<String, Object> getMessageCode(int code, String message, Map<String, Object> map) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("code", code);
        result.put("message", message);
        result.put("data", map);
        return result;
    }

    public static String getCodeMessage(int code) {
        String key = code + "";
        return resultCodeConf.get(key);
    }

}