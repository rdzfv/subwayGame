package com.xyy.subway.trueword.service.util;



import com.xyy.subway.trueword.utils.Utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author     ：xyy
 * @date       ：Created in 2019/12/20
 * @description： 七牛配置
 * @version:     1.0.0
 */

public class QiniuConfiguration {
    private static QiniuConfiguration instance;
    private Map<String, String> qiniuConf;

    private QiniuConfiguration() {
        qiniuConf = Utils.load_conf("/qiniu.properties");
    }

    public static QiniuConfiguration getInstance() {
        if (instance == null) {
            instance = new QiniuConfiguration();
        }
        return instance;
    }

    public int getLogSaveDays() {
        String day = qiniuConf.get("uploadserver.log.day");
        return Integer.parseInt(day);
    }

    public String getUploadServerLogPath() {
        return qiniuConf.get("uploadserver.log.path");
    }

    public String getQiniuAccessKey() {
        return qiniuConf.get("qiniu.server.accesskey");
    }

    public String getQiniuSecretKey() {
        return qiniuConf.get("qiniu.server.secretkey");
    }

    public String getQiniuDefaultBucket() {
        return qiniuConf.get("qiniu.server.bucket.default");
    }

    public String getQiniuAccessUrlPrefix() {
        return qiniuConf.get("qiniu.server.accessurl.prefix");
    }

    public Set<String> getRequestAccessIpList() {
        String ipList = qiniuConf.get("request.access.ip.list");
        Set<String> ipSet = new HashSet<String>();
        for (String ip : ipList.split(";")) {
            ipSet.add(ip);
        }
        return ipSet;
    }

    public String getPersistentPipeline() {
        return qiniuConf.get("qiniu.server.persistent.pipeline");
    }

    public String getVedioAvthumbNotify() {
        return qiniuConf.get("qiniu.server.video.avthumb.notify");
    }
}

