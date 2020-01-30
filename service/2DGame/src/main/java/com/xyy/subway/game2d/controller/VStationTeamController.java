package com.xyy.subway.game2d.controller;

import com.xyy.subway.game2d.entity.VStationTeam;
import com.xyy.subway.game2d.entity.VStationTeamType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.VStationStoreService;
import com.xyy.subway.game2d.service.VStationTeamService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xyy
 * @date 2020/1/28 12:25
 * @description
 */
@Api(value="VStationStoreController",tags={"虚拟站点商店接口"})
@Controller("/vstationteam")
@RequestMapping("/vstationteam")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VStationTeamController extends BaseController {
    // 引入
    @Autowired
    private VStationTeamService vStationTeamService;


    /**
     * @author xyy
     * @date 2020/1/27 14:47
     */
    @ApiOperation(value="通过id获取虚拟站点队伍信息", tags={}, notes="")
    @RequestMapping(value = "/getVStationTeamInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点队伍id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getVStationTeamInfoById(@ApiParam(name="id", value = "虚拟站点队伍id", required = true) int id) throws BusinessException {
        VStationTeam vStationTeam = vStationTeamService.getVStationTeamInfoById(id);
        if (vStationTeam == null) throw new BusinessException(EnumBusinessError.VSTATIONTEAM_NOT_EXIST);
        return CommonReturnType.create(vStationTeam);
    }



    /**
     * @author xyy
     * @date 2020/1/27 19:31
     */
    @ApiOperation(value="通过id获取虚拟站点队伍类型信息", tags={}, notes="")
    @RequestMapping(value = "/getVStationTeamTypeInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点队伍类型id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getVStationTeamTypeInfoById(@ApiParam(name="id", value = "虚拟站点队伍类型id", required = true) int id) throws BusinessException {
        VStationTeamType vStationTeamType = vStationTeamService.getVStationTeamTypeInfoById(id);
        if (vStationTeamType == null) throw new BusinessException(EnumBusinessError.VSTATIONTEAMTYPE_NOT_EXIST);
        return CommonReturnType.create(vStationTeamType);
    }
}
