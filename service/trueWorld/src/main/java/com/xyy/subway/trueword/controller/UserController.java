package com.xyy.subway.trueword.controller;

import com.xyy.subway.trueword.entity.UserInfo;
import com.xyy.subway.trueword.error.BusinessException;
import com.xyy.subway.trueword.error.EnumBusinessError;
import com.xyy.subway.trueword.model.UserInfoDetail;
import com.xyy.subway.trueword.response.CommonReturnType;
import com.xyy.subway.trueword.service.UserService;
import com.xyy.subway.trueword.service.util.HttpClient;
import com.xyy.subway.trueword.utils.Utils;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;

/**
 * @author xyy
 * @date 2020/1/25 9:01
 * @description
 */
@Api(value="UserController",tags={"用户接口"})
@Controller("/user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {
    // 引入
    @Autowired
    private UserService userService;
    @Autowired
    private HttpClient httpClient;


    /**
     * @author xyy
     * @date 2020/1/22 20:27
     */
    @ApiOperation(value="通过用户id获取用户信息", tags={}, notes="")
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getUserInfoByUserId(@ApiParam(name="id", value = "用户id", required = true) int id) throws BusinessException {
        UserInfo userInstanse = userService.getUserInfoById(id);
        if (userInstanse == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        return CommonReturnType.create(userInstanse);
    }


    /**
     * @author xyy
     * @date 2020/1/25 13:18
    */
    @ApiOperation(value="用户登录", tags={}, notes="")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="code", value="微信code", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType login(@ApiParam(name="code", value = "微信code", required = true) String code) throws BusinessException {
        System.out.println(code);
        // 入参校验
        if (StringUtils.isEmpty(code) ){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 通过code获取openid
        // 发送http请求到微信平台
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc80f6fe4d1258003&&secret=77e61076f62a3bac86263af8d340905f&&js_code=" + code + "&grant_type=authorization_code";
        //get请求
        HttpMethod method = HttpMethod.GET;
        // 发送http请求并返回结果
        String wechatResult = httpClient.client_GET(url,method);
        System.out.println(wechatResult);
        // string结果转为json并获取openid
        Utils utils = new Utils();
        JSONObject wechatReturnJSON = utils.String2Json(wechatResult);
        if (wechatReturnJSON.get("openid") == null || wechatReturnJSON.get("session_key") == null) throw new BusinessException(EnumBusinessError.ILLEGAL_REQUEST);
        String openid = wechatReturnJSON.get("openid").toString();
        String session_key = wechatReturnJSON.get("session_key").toString();

        // 用户登录服务
        UserInfo userInstanse = userService.login(code);
        if (userInstanse == null) throw new BusinessException(EnumBusinessError.NEW_USER);
        return CommonReturnType.create(userInstanse);
    }


    /**
     * @author xyy
     * @date 2020/1/25 14:04
    */
    @ApiOperation(value="用户信息更新", tags={}, notes="")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType updateUserInfo(UserInfoDetail userInfoDetail) throws BusinessException {
        // 入参校验
        if (StringUtils.isEmpty(userInfoDetail.getCode()) ){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 通过code获取openid
        // 发送http请求到微信平台
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=wxc80f6fe4d1258003&&secret=77e61076f62a3bac86263af8d340905f&&js_code=" + userInfoDetail.getCode() + "&grant_type=authorization_code";
        //get请求
        HttpMethod method = HttpMethod.GET;
        // 发送http请求并返回结果
        String wechatResult = httpClient.client_GET(url,method);
        System.out.println(wechatResult);
        // string结果转为json并获取openid
        Utils utils = new Utils();
        JSONObject wechatReturnJSON = utils.String2Json(wechatResult);
        if (wechatReturnJSON.get("openid") == null || wechatReturnJSON.get("session_key") == null) throw new BusinessException(EnumBusinessError.ILLEGAL_REQUEST);
        String openid = wechatReturnJSON.get("openid").toString();
        String session_key = wechatReturnJSON.get("session_key").toString();

        // 用户更新资料
        UserInfoDetail userInstanse = userService.updateUserInfo(userInfoDetail);
        if (userInstanse == null) throw new BusinessException(EnumBusinessError.NEW_USER);
        return CommonReturnType.create(userInstanse);
    }
}
