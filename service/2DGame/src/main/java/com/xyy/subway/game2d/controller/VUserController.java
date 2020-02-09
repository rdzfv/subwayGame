package com.xyy.subway.game2d.controller;


import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.dto.VRouteStationDTO;
import com.xyy.subway.game2d.entity.*;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.ToolService;
import com.xyy.subway.game2d.service.VRouteService;
import com.xyy.subway.game2d.service.VStationService;
import com.xyy.subway.game2d.service.VUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private VRouteService vRouteService;
    @Autowired
    private VStationService vStationService;
    @Autowired
    private ToolService toolService;


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
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel(vuserInstanse.getExp());

        JSONObject object = new JSONObject();
        object.put("user", vuserInstanse);
        object.put("levelDetail", expAndLevelDTO);

        return CommonReturnType.create(object);
    }



    /**
     * @author xyy
     * @date 2020/1/27 12:17
    */
    @ApiOperation(value="更新v用户信息", tags={}, notes="用户id必传，其他可省")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType updateUserInfo(VUser vUser) throws BusinessException {
        VUser vuserInstanse = vuserService.updateUserInfo(vUser);
        if (vuserInstanse == null) throw new BusinessException(EnumBusinessError.USERINFO_UPDATE_FAILED);
        return CommonReturnType.create(vuserInstanse);
    }



    /**
     * @author xyy
     * @date 2020/1/22 20:27
     */
    @ApiOperation(value="通过用户id获取用户虚拟地铁线信息", tags={}, notes="")
    @RequestMapping(value = "/getUserRouteStationById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getUserRouteStationById(@ApiParam(name="id", value = "用户id", required = true) int id) throws BusinessException {
        List<VRoute> vRoutes = vRouteService.getVRoutesInfoByUserId(id);
        ArrayList<VRouteStationDTO> vRouteStationDTOS = new ArrayList<>();
        if (vRoutes == null) {
            return CommonReturnType.create(null);
        }
        for (int i = 0; i < vRoutes.size(); i ++) {
            String stationsStr = vRoutes.get(i).getVstationIds();
            int routeId = vRoutes.get(i).getId();
            String routeName = vRoutes.get(i).getName();
            String[] stationIds = stationsStr.split(",");
            for (int j = 0; j < stationIds.length; j ++) {
                VRouteStationDTO vRouteStationDTO = new VRouteStationDTO();
                int stationId = Integer.parseInt(stationIds[j]);
                VStation vStation = vStationService.getVStationInfoById(stationId);
                String stationName = vStation.getName();
                vRouteStationDTO.setUserId(id);
                vRouteStationDTO.setRouteId(routeId);
                vRouteStationDTO.setRouteName(routeName);
                vRouteStationDTO.setRouteStationId(stationId);
                vRouteStationDTO.setRouteStationName(stationName);
                vRouteStationDTOS.add(vRouteStationDTO);
            }
        }
        return CommonReturnType.create(vRouteStationDTOS);
    }



    /**
     * @author xyy
     * @date 2020/2/9 19:47
    */
    @ApiOperation(value="通过等级获取各项服务的可用情况", tags={}, notes="")
    @RequestMapping(value = "/getServiceStatusByLevel", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="level", value="等级", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getServiceStatusByLevel(@ApiParam(name="level", value = "等级", required = true) int level) throws BusinessException {
        JSONObject jsonObject = toolService.listCanAndCantByLevel(level);
        return CommonReturnType.create(jsonObject);
    }
}
