package com.xyy.subway.trueword.httpdto;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;



/**
 * @author     ：xyy
 * @date       ：Created in 2019/12/20
 * @description：
 * @version:     1.0.0
 */

public class Utils {
    public static final byte[] intToByteArray(int value) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
        return bytes;
    }


    public static final int bytesToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value = (value << 8) | (bytes[i] & 0xff);
        }
        return value;
    }

    /**
     * exec shell
     *
     * @param shell
     */
    public static void execShell(String shell) {
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec(shell);
        } catch (Exception e) {
            // TODO
        }
    }

    /**
     * run shell cmd
     *
     * @param shell
     * @return
     */
    public static List<String> runShell(String shell) {
        List<String> result = new ArrayList<String>();

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shell});
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            // TODO
        }

        return result;
    }

    /**
     * 将map转化为字符串 格式：key1:value1,key2:value2,...
     *
     * @param <T>
     * @return
     */
    public static <T> String map2String(Map<String, T> map) {
        StringBuffer sb = new StringBuffer();
        boolean flag = false;
        for (String key : map.keySet()) {
            if (flag == true) {
                sb.append(",");
            }
            sb.append(key).append(":").append(map.get(key).toString());
            flag = true;
        }
        return sb.toString();
    }

    /**
     * load config
     *
     * @param conf
     * @return
     */
    public static Map<String, String> load_conf(String conf) {
        Map<String, String> config = Collections.synchronizedMap(new LinkedHashMap<String, String>());
        Properties p = Utils.load_properties(conf);
        for (Entry<Object, Object> e : p.entrySet()) {
            config.put(String.valueOf(e.getKey()), String.valueOf(e.getValue()));
        }

        return config;
    }

    /**
     * load properties
     *
     * @param conf
     * @return
     */
    public static Properties load_properties(String conf) {
        Properties p = new Properties();
        try {
            InputStream pIs = Utils.class.getResourceAsStream(conf);
            p.load(pIs);
            pIs.close();
        } catch (IOException e) {
            // TODO
        }
        return p;
    }

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url         请求的URL地址
     * @param queryParams 拼接的查询参数字符串 or map
     * @param charSet     编码
     * @return 返回请求响应的HTML
     * @throws IOException
     */
    public static String doGet(String url, Object queryParams, String charSet) throws IOException {
        return doHttpRequest("get", url, queryParams, charSet, null, 0);
    }

    /**
     * 执行一个HTTP GET请求，返回请求响应的HTML
     *
     * @param url
     * @param queryParams 拼接的字符串 or map
     * @return
     * @throws URIException
     * @throws IOException
     */
    public static String doGet(String url, Object queryParams, String charSet, String proxyIp, int proxyPort)
            throws URIException, IOException {
        return doHttpRequest("get", url, queryParams, charSet, proxyIp, proxyPort);
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url    请求的URL地址
     * @param params 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charSet) throws IOException {
        return doHttpRequest("post", url, params, charSet, null, 0);
    }

    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url    请求的URL地址
     * @param params 请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     * @throws IOException
     */
    public static String doPost(String url, Map<String, String> params, String charSet, String proxyIp, int proxyPort)
            throws IOException {
        return doHttpRequest("post", url, params, charSet, proxyIp, proxyPort);
    }

    @SuppressWarnings("unchecked")
    private static String doHttpRequest(String methodName, String url, Object paramObj, String charSet, String proxyIp,
                                        int proxyPort) throws IOException {
        String response = "";
        HttpClient client = new HttpClient();
        HttpMethod method = null;

        // 设置参数
        if (methodName == "post") {
            method = new PostMethod(url);
            // post参数
            if (paramObj != null) {
                Map<String, String> params = (Map<String, String>) paramObj;
                HttpMethodParams p = new HttpMethodParams();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    p.setParameter(entry.getKey(), entry.getValue());
                }
                method.setParams(p);
            }
        } else {
            method = new GetMethod(url);
            String queryString = "";
            if (paramObj instanceof Map) {
                Map<String, String> params = (Map<String, String>) paramObj;
                boolean flag = false;
                for (String key : params.keySet()) {
                    if (flag) {
                        queryString += "&";
                    }
                    flag = true;
                    queryString += key + "=" + params.get(key);
                }
            } else if (paramObj instanceof String) {
                queryString = (String) paramObj;
            }
            if (StringUtils.isNotBlank(queryString)) {
                method.setQueryString(URIUtil.encodeQuery(queryString));
            }
        }

        // 设置代理
        if (proxyIp != null && proxyIp.length() > 0) {
            client.getHostConfiguration().setProxy(proxyIp, proxyPort);
        }

        // 设置编码
        if (charSet == null || charSet.length() <= 0) {
            charSet = "utf-8";
        }
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);

        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                response = method.getResponseBodyAsString();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            method.releaseConnection();
        }

        return response;
    }

    /**
     * 判断参数是否为空
     *
     * @param obj 校验的对象
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof List) {
            return obj == null || ((List<?>) obj).size() == 0;
        } else if (obj instanceof Number) {
            DecimalFormat decimalFormat = new DecimalFormat();
            try {
                return decimalFormat.parse(obj.toString()).doubleValue()==0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return obj == null || "".equals(obj.toString());
        }
    }

    /**
     * 判断参数是否为空,多参数
     *
     * @param objects 校验的对象群
     * @return
     */
    public static boolean isEmpty(Object... objects) {
        boolean flag = false;
        for (Object obj : objects) {
            flag = flag || isEmpty(obj);
        }
        return flag;
    }

    /**
     * 判断是否是ajax请求
     *
     * @param httpRequest
     * @return
     */
    public static boolean isAjax(HttpServletRequest httpRequest) {
        String xRequestedWith = httpRequest.getHeader("X-Requested-With");
        return (xRequestedWith != null && "XMLHttpRequest".equals(xRequestedWith));
    }

    /**
     * md5加密
     *
     * @param text 待加密字符串
     * @return
     */
    public static String md5(String text) {
        String encodeStr = "";
        try {
            encodeStr = DigestUtils.md5Hex(text);
        } catch (Exception e) {
            return encodeStr;
        }
        return encodeStr;
    }

    /**
     * 返回默认的SnowflakeIdWorker实例，指定workerid为0
     *
     * @return
     */

    /**
     * 获取服务器IP
     *
     * @return
     */
    public static String getHostAddress() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "127.0.0.1";
    }


    public static String getExpectionMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            // 将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }

    /**
     * 统一处理api参数错误
     *
     * @param bindingResult
     * @return
     */
    public static Map<String, Object> returnParamterError(BindingResult bindingResult) {
        List<ObjectError> list = bindingResult.getAllErrors();
        for (ObjectError objectError : list) {
            return MsgCodeUtils.getMessageCode(MsgCodeUtils.FAIL, objectError.getDefaultMessage());
        }
        return MsgCodeUtils.getMessageCode(MsgCodeUtils.FAIL);
    }


    /**
     * 获取一定范围内的随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;

    }







}
