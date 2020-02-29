package com.xyy.subway.game2d.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.ar.ResultInfo;
import com.xyy.subway.game2d.ar.WebAR;
import com.xyy.subway.game2d.entity.DailyTask;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.DailyTaskService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/29 14:11
 * @description
 */

@Api(value="tool",tags={"每日任务接口"})
@Controller("/tool")
@RequestMapping("/tool")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ToolController extends BaseController {

    /**
     * @author xyy
     * @date 2020/2/29 14:20
    */
    @ApiOperation(value="AR测试接口", tags={}, notes="")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example="0")
//    })
    @ResponseBody
    public CommonReturnType test(@RequestBody String image) throws BusinessException {
        String cloudKey = "87e2719dbaca3b9c399b7c6f4826465e";
        String cloudSecret = "nkK9Skk9YY9jLeUdBJcj4NDBLthDIbGxjjhZMmpj9uX8MRfcWqJocEf3XN6hdXPNYLGdimK21Ph97oBc40xxrqjUH4UT3x6cxqsaYHyBgNQNlrOZYO9HozOANAzgC2gK";
        String cloudUrl = "http://89f76fb61ca7de2f23b8d381f4275be2.cn1.crs.easyar.com:8080/search";

        WebAR webAR = new WebAR(cloudKey, cloudSecret, cloudUrl);

        // 取出Image
        JSONObject detail = JSONObject.parseObject(image);
        String imageResult = (String)detail.get("image");
        System.out.println(imageResult);
        ResultInfo info = new ResultInfo();
        try {
            // 图片的base64数据，使用前请更换为你的图片数据
            info = webAR.recognize(imageResult);
            System.out.println(info);

            if (info.getStatusCode() == 0) {
                // statusCode为0时，识别到目标，数据在target中
                System.out.println(info.getResult().getTarget().getTargetId());
            } else {
                // 未识别到目标
                System.out.println(info.getStatusCode());
                System.out.println(info.getResult().getTarget().getName());
                return CommonReturnType.create(info);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return CommonReturnType.create(e.getMessage());
        }
        return CommonReturnType.create(info);
    }
}
