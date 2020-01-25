package com.xyy.subway.trueword.controller;

import com.xyy.subway.trueword.bean.Setting;
import com.xyy.subway.trueword.service.util.QiniuConfiguration;
import com.xyy.subway.trueword.utils.UploadUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xyy
 * @date 2020/1/25 15:00
 * @description
 */

@Controller("/upload")
@RequestMapping("/upload")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UploadController {
    @ApiOperation("上传文件到七牛")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> uploadSubmit(MultipartFile upfile, HttpServletRequest request) throws Exception {

        //创建目标文件
        String extension = UploadUtils.getExtension(upfile.getOriginalFilename());
        String filename = UUID.randomUUID().toString() + extension;
        File parentDir = new File(request.getServletContext().getRealPath("/upload"));
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
        File file = new File(parentDir, filename);
        upfile.transferTo(file);
        String bucket = QiniuConfiguration.getInstance().getQiniuDefaultBucket();
        String accessKey = QiniuConfiguration.getInstance().getQiniuAccessKey();
        String secretKey = QiniuConfiguration.getInstance().getQiniuSecretKey();

        UploadUtils.uploadWithQiniu(file, accessKey, secretKey, bucket);

        file.delete();

        Map<String, String> result = new HashMap<String, String>();
        result.put("state", "SUCCESS");
        result.put("original", upfile.getOriginalFilename());
        result.put("title", filename);
        result.put("type", extension);
        result.put("url", "http://xuyuyan.cn/" + filename);
        return result;
    }

}
