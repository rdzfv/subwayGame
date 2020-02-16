package com.xyy.subway.game2d.controller;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dto.VBuildTeamTypeDetailDTO;
import com.xyy.subway.game2d.dto.VTeamTypeDetailDTO;
import com.xyy.subway.game2d.entity.VStationTeam;
import com.xyy.subway.game2d.entity.VStationTeamType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.TeamDetailService;
import com.xyy.subway.game2d.service.VStationStoreService;
import com.xyy.subway.game2d.service.VStationTeamService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import net.sf.json.JSONArray;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:25
 * @description
 */
@Api(value="VStationTeamController",tags={"虚拟站点队伍接口"})
@Controller("/vstationteam")
@RequestMapping("/vstationteam")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VStationTeamController extends BaseController {
    // 引入
    @Autowired
    private VStationTeamService vStationTeamService;
    @Autowired
    private TeamDetailService teamDetailService;


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




     /**
      * @author xyy
      * @date 2020/2/10 19:00
     */
    @ApiOperation(value="新建一个建筑团队", tags={}, notes="")
    @RequestMapping(value = "/newABuildingTeam", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="stationId", value="地铁站id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="level", value="建造团队等级数", dataType="int", paramType = "query", example=""),
    })
    @ResponseBody
    public CommonReturnType newABuildingTeam(@ApiParam(name="id", value = "用户id", required = true) int id,
                                             @ApiParam(name="stationId", value = "地铁站id", required = true) int stationId,
                                             @ApiParam(name="level", value = "建造团队等级数", required = true) int level
    ) throws BusinessException {
        JSONObject jsonObject = vStationTeamService.newABuildingTeam(id, stationId, level);
        return CommonReturnType.create(jsonObject);
    }





    /**
     * @author xyy
     * @date 2020/2/10 19:00
     */
    @ApiOperation(value="新建一个清洁/服务/安保团队", tags={}, notes="type为2是清洁团队，type为3是服务中心，type为3是安保团队")
    @RequestMapping(value = "/newAOtherTeam", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="stationId", value="地铁站id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="level", value="团队等级数", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="type", value="团队种类", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType newAOtherTeam(@ApiParam(name="id", value = "用户id", required = true) int id,
                                          @ApiParam(name="stationId", value = "地铁站id", required = true) int stationId,
                                          @ApiParam(name="level", value = "建造团队等级数", required = true) int level,
                                          @ApiParam(name="type", value = "团队种类", required = true) int type
    ) throws BusinessException {
        JSONObject jsonObject = vStationTeamService.newATeam(id, stationId, level, type);
        return CommonReturnType.create(jsonObject);
    }




    /**
     * @author xyy
     * @date 2020/2/15 19:09
    */
    @ApiOperation(value="修改建筑队伍类型的信息", tags={}, notes="")
    @RequestMapping(value = "/updateBuildingTeamTypeInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType updateBuildingTeamTypeInfo(VBuildTeamTypeDetailDTO vBuildTeamTypeDetailDTO) throws BusinessException {
        // 取出上传的等级数
        int level = vBuildTeamTypeDetailDTO.getLevel();

        List<VBuildTeamTypeDetailDTO> vBuildTeamTypeDetailDTOS = teamDetailService.checkBuildingTeamDetail(1);
        vBuildTeamTypeDetailDTOS.get(level - 1).setPicUrl(vBuildTeamTypeDetailDTO.getPicUrl());
        vBuildTeamTypeDetailDTOS.get(level - 1).setIncreaseWorker(vBuildTeamTypeDetailDTO.getIncreaseWorker());
        vBuildTeamTypeDetailDTOS.get(level - 1).setVisitor(vBuildTeamTypeDetailDTO.getVisitor());
        vBuildTeamTypeDetailDTOS.get(level - 1).setCost(vBuildTeamTypeDetailDTO.getCost());
        vBuildTeamTypeDetailDTOS.get(level - 1).setUnlockedIn(vBuildTeamTypeDetailDTO.getUnlockedIn());
        vBuildTeamTypeDetailDTOS.get(level - 1).setUpExp(vBuildTeamTypeDetailDTO.getUpExp());

        System.out.println(JSONArray.fromObject(vBuildTeamTypeDetailDTOS).toString());

        // 写回数据库
        VStationTeamType vStationTeamType = vStationTeamService.updateBuildingTeamTypeDetail(JSONArray.fromObject(vBuildTeamTypeDetailDTOS).toString());
        return CommonReturnType.create(vStationTeamType);
    }




    /**
     * @author xyy
     * @date 2020/2/15 19:09
     */
    @ApiOperation(value="修改其他队伍类型的信息", tags={}, notes="")
    @RequestMapping(value = "/updateOtherTeamTypeInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType updateOtherTeamTypeInfo(VTeamTypeDetailDTO vTeamTypeDetailDTO) throws BusinessException {
        // 取出上传的等级数和团队类型
        int type = vTeamTypeDetailDTO.getType();
        int level = vTeamTypeDetailDTO.getLevel();

        List<VTeamTypeDetailDTO> vTeamTypeDetailDTOS = teamDetailService.checkOtherBuildingTeamDetail(type);
        vTeamTypeDetailDTOS.get(level - 1).setPicUrl(vTeamTypeDetailDTO.getPicUrl());
        vTeamTypeDetailDTOS.get(level - 1).setIncreaseUncrowded(vTeamTypeDetailDTO.getIncreaseUncrowded());
        vTeamTypeDetailDTOS.get(level - 1).setVisitor(vTeamTypeDetailDTO.getVisitor());
        vTeamTypeDetailDTOS.get(level - 1).setIncreaseTidy(vTeamTypeDetailDTO.getIncreaseTidy());
        vTeamTypeDetailDTOS.get(level - 1).setCost(vTeamTypeDetailDTO.getCost());
        vTeamTypeDetailDTOS.get(level - 1).setUnlockedIn(vTeamTypeDetailDTO.getUnlockedIn());
        vTeamTypeDetailDTOS.get(level - 1).setUpExp(vTeamTypeDetailDTO.getUpExp());
        vTeamTypeDetailDTOS.get(level - 1).setIncreaseSafe(vTeamTypeDetailDTO.getIncreaseSafe());

        System.out.println(JSONArray.fromObject(vTeamTypeDetailDTOS).toString());

        // 写回数据库
        VStationTeamType vStationTeamType = vStationTeamService.updateOtherTeamTypeDetail(JSONArray.fromObject(vTeamTypeDetailDTOS).toString(), type);
        return CommonReturnType.create(vStationTeamType);
    }
}
