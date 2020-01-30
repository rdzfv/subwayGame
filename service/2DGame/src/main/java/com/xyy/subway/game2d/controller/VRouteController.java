package com.xyy.subway.game2d.controller;

import com.xyy.subway.game2d.entity.VRoute;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.VRouteService;
import com.xyy.subway.game2d.service.VUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xyy
 * @date 2020/1/28 12:03
 * @description
 */
@Api(value="VRouteController",tags={"虚拟路线接口"})
@Controller("/vroute")
@RequestMapping("/vroute")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VRouteController extends BaseController {
    // 引入
    @Autowired
    private VRouteService vRouteService;


    /**
     * @author xyy
     * @date 2020/1/27 14:34
     */
    @ApiOperation(value="通过id获取虚拟路线信息", tags={}, notes="")
    @RequestMapping(value = "/getVRouteInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟路线id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getVRouteInfoById(@ApiParam(name="id", value = "虚拟路线id", required = true) int id) throws BusinessException {
        VRoute vRouteInstanse = vRouteService.getVRouteInfoById(id);
        if (vRouteInstanse == null) throw new BusinessException(EnumBusinessError.VROUTE_NOT_EXIST);
        return CommonReturnType.create(vRouteInstanse);
    }
}