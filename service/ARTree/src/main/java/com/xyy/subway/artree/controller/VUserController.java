package com.xyy.subway.artree.controller;

import com.xyy.subway.artree.entity.VUser;
import com.xyy.subway.artree.error.BusinessException;
import com.xyy.subway.artree.error.EnumBusinessError;
import com.xyy.subway.artree.response.CommonReturnType;
import com.xyy.subway.artree.service.VUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xyy
 * @date 2020/1/25 9:01
 * @description
 */
@Api(value="VUserController",tags={"用户接口"})
@Controller("/user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VUserController extends BaseController {
    // 引入
    @Autowired
    private VUserService vuserService;


    /**
     * @author xyy
     * @date 2020/1/22 20:27
     */
    @ApiOperation(value="通过用户id获取v用户信息", tags={}, notes="")
    @RequestMapping(value = "/getUserInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getUserInfoByUserId(@ApiParam(name="id", value = "用户id", required = true) int id) throws BusinessException {
        VUser vuserInstanse = vuserService.getVUserInfoById(id);
        if (vuserInstanse == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        return CommonReturnType.create(vuserInstanse);
    }
}
