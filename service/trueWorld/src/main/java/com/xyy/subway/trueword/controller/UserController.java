package com.xyy.subway.trueword.controller;

import com.xyy.subway.trueword.controller.BaseController;
import com.xyy.subway.trueword.dto.UpdateUserInfoDTO;
import com.xyy.subway.trueword.entity.UserInfo;
import com.xyy.subway.trueword.error.BusinessException;
import com.xyy.subway.trueword.error.EnumBusinessError;
import com.xyy.subway.trueword.dto.FriendsDTO;
import com.xyy.subway.trueword.dto.ShowUserInfoDTO;
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

import java.util.ArrayList;

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
    @ApiOperation(value="通过用户id获取用户信息", tags={}, notes="测试")
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
    @ApiOperation(value="用户登录", tags={}, notes="前端获取code，传递给后台，后台通过code向微信开放平台获取openId，查询数据库，确定是否是已有用户，如果是已有用户就返回用户信息，如果不是已有用户，返回错误码20002。前端给出提示引导用户填写基本信息。")
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
        System.out.println("用户openId" + openid);

        // 用户登录服务
        ShowUserInfoDTO showUserInfoDTO = userService.login(openid);

        // 判断是否是新用户
        if (showUserInfoDTO == null) {
            // 新增一个用户
            UserInfo userInfo = new UserInfo();
            userInfo.setOpenId(openid);
            userInfo.setIcon_url("");
            userInfo.setName("");
            userInfo.setFriend_ids("");
            userInfo.setSessionKey("");
            userInfo.setToken("");

            showUserInfoDTO = userService.newAUser(userInfo);

        }
        return CommonReturnType.create(showUserInfoDTO);
    }


    /**
     * @author xyy
     * @date 2020/1/25 14:04
    */
    @ApiOperation(value="用户信息更新", tags={}, notes="（适用于新用户、老用户）返回更新后的用户信息。三个参数可以不用给全，id必给。如果是新用户,isNewUser填1")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO) throws BusinessException {
        ShowUserInfoDTO userInstanse = new ShowUserInfoDTO();
        // 判断是否是新用户
        if (updateUserInfoDTO.getIsNewUser() == -1) {
            // 新增他的2d游戏账户
            // 发送http请求到game2d服务
            String url = "http://www.xuyuyan.com:9100/fwwb/game2d/user/newAVUserByUserId?userId=" + userInstanse.getUserId();
            //get请求
            HttpMethod method = HttpMethod.GET;
            // 发送http请求并返回结果
            String Result = httpClient.client_GET(url,method);
            System.out.println(Result);

        }

        // 用户更新资料
        userInstanse = userService.updateUserInfo(updateUserInfoDTO);

        return CommonReturnType.create(userInstanse);
    }


    /**
     * @author xyy
     * @date 2020/1/25 16:14
    */
    @ApiOperation(value="查看我的好友列表", tags={}, notes="返回好友的用户id，用户名，头像图片，加入游戏日期，好友状态")
    @RequestMapping(value = "/listMyFriends", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType listMyFriends(@ApiParam(name="id", value = "用户id", required = true) int id) throws BusinessException {
        ArrayList<FriendsDTO> users = userService.listMyFriends(id);
        return CommonReturnType.create(users);
    }



    /**
     * @author xyy
     * @date 2020/1/25 16:56
    */
    @ApiOperation(value="发送好友申请", tags={}, notes="")
    @RequestMapping(value = "/applyForFriend", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="friendName", value="朋友name", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType applyForFriend(@ApiParam(name="id", value = "我的用户id", required = true) int id,
                                           @ApiParam(name="friendName", value = "朋友name", required = true) String friendName
    ) throws BusinessException {
        UserInfo userResult = userService.applyForFriend(id, friendName);
        return CommonReturnType.create(userResult);
    }



    /**
     * @author xyy
     * @date 2020/1/25 16:56
     */
    @ApiOperation(value="通过好友申请", tags={}, notes="")
    @RequestMapping(value = "/agreeForFriend", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0"),
            @ApiImplicitParam(name="friendId", value="朋友id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType agreeForFriend(@ApiParam(name="id", value = "我的用户id", required = true) int id,
                                           @ApiParam(name="friendId", value = "朋友id", required = true) int friendId
    ) throws BusinessException {
        UserInfo userResult = userService.agreeForFriend(id, friendId);
        return CommonReturnType.create(userResult);
    }



    /**
     * @author xyy
     * @date 2020/1/26 10:47
    */
    @ApiOperation(value="拒绝好友申请", tags={}, notes="")
    @RequestMapping(value = "/rejectForFriend", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0"),
            @ApiImplicitParam(name="friendId", value="朋友id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType rejectForFriend(@ApiParam(name="id", value = "我的用户id", required = true) int id,
                                           @ApiParam(name="friendId", value = "朋友id", required = true) int friendId
    ) throws BusinessException {
        UserInfo userResult = userService.rejectForFriend(id, friendId);
        return CommonReturnType.create(userResult);
    }



    /**
     * @author xyy
     * @date 2020/1/25 20:47
    */
    @ApiOperation(value="删除好友", tags={}, notes="")
    @RequestMapping(value = "/deleteFriend", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0"),
            @ApiImplicitParam(name="friendId", value="朋友id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType deleteFriend(@ApiParam(name="id", value = "我的用户id", required = true) int id,
                                           @ApiParam(name="friendId", value = "朋友id", required = true) int friendId
    ) throws BusinessException {
        UserInfo userResult = userService.deleteFriend(id, friendId);
        return CommonReturnType.create(userResult);
    }
}
