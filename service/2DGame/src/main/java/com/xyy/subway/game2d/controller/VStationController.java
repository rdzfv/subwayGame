package com.xyy.subway.game2d.controller;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dto.VStationStoreDTO;
import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VStationStoreType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.VRouteService;
import com.xyy.subway.game2d.service.VStationService;
import com.xyy.subway.game2d.service.VStationStoreService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:10
 * @description
 */
@Api(value="VStationController",tags={"虚拟站点接口"})
@Controller("/vstation")
@RequestMapping("/vstation")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VStationController extends BaseController {

    // 引入
    @Autowired
    private VStationService vStationService;
    @Autowired
    private VStationStoreService vStationStoreService;


    /**
     * @author xyy
     * @date 2020/1/27 14:29
     */
    @ApiOperation(value="通过id获取虚拟站点信息", tags={}, notes="")
    @RequestMapping(value = "/getVStationInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getVStationInfoById(@ApiParam(name="id", value = "虚拟站点id", required = true) int id) throws BusinessException {
        VStation vStationInstanse = vStationService.getVStationInfoById(id);
        if (vStationInstanse == null) throw new BusinessException(EnumBusinessError.VSTATION_NOT_EXIST);
        return CommonReturnType.create(vStationInstanse);
    }




    /**
     * @author xyy
     * @date 2020/1/28 13:56
    */
    @ApiOperation(value="更新虚拟站点信息", tags={}, notes="")
    @RequestMapping(value = "/updateVStationInfo", method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType updateVStationInfo(VStation vStation) throws BusinessException {
        VStation vStationInstanse = vStationService.updateVStationInfo(vStation);
        if (vStationInstanse == null) throw new BusinessException(EnumBusinessError.VSTATION_NOT_EXIST);
        return CommonReturnType.create(vStationInstanse);
    }



    /**
     * @author xyy
     * @date 2020/1/28 15:59
    */
    @ApiOperation(value="通过stationId获取station和store详情", tags={}, notes="")
    @RequestMapping(value = "/getStationStoreInfoByStationId", method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点id", dataType="int", paramType = "query", example="")
    })
    public CommonReturnType getStationStoreInfoByStationId(@ApiParam(name="id", value = "虚拟站点id", required = true) int id) throws BusinessException {
        // 查询站点基本信息
        List<VStationStore> vStationStores = vStationStoreService.getVStationStoreInfoByStationId(id);
        ArrayList<VStationStoreDTO> vStationStoreDTOS = new ArrayList<>();
        for (int i = 0; i < vStationStores.size(); i ++) {
            int storeId = vStationStores.get(i).getId();
            String storeName = vStationStores.get(i).getName();
            int storeTypeId = vStationStores.get(i).getType();
            int storeLevel = vStationStores.get(i).getLevel();
            int storePosition = vStationStores.get(i).getPosition();
            int storeStatus = vStationStores.get(i).getStatus();
            Long remainTime = vStationStores.get(i).getRemainTime();
            Float avaliableProfit = vStationStores.get(i).getAvailableProfit();
            Float maxProfit = vStationStores.get(i).getMaxProfit();
            int isDeleted = vStationStores.get(i).getIsDeleted();

            // 根据storeType获取相应升级信息
            VStationStoreType vStationStoreType = vStationStoreService.getVStationStoreTypeInfoById(storeTypeId);
            String storeTypeName = vStationStoreType.getName();
            String levelUpJSON = vStationStoreType.getDetail();

            VStationStoreDTO vStationStoreDTO = new VStationStoreDTO();
            vStationStoreDTO.setId(storeId);
            vStationStoreDTO.setName(storeName);
            vStationStoreDTO.setLevel(storeLevel);
            vStationStoreDTO.setPosition(storePosition);
            vStationStoreDTO.setStatus(storeStatus);
            vStationStoreDTO.setRemainTime(remainTime);
            vStationStoreDTO.setType(storeTypeName);
            vStationStoreDTO.setLevelUpJSON(levelUpJSON);
            vStationStoreDTO.setAvailableProfit(avaliableProfit);
            vStationStoreDTO.setMaxProfit(maxProfit);
            vStationStoreDTO.setIsDeleted(isDeleted);


            vStationStoreDTOS.add(vStationStoreDTO);
        }
        return CommonReturnType.create(vStationStoreDTOS);
    }



    /**
     * @author xyy
     * @date 2020/2/2 15:49
    */
    @ApiOperation(value="新建一个地铁站", tags={}, notes="")
    @RequestMapping(value = "/newAStation", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="0"),
            @ApiImplicitParam(name="routeId", value="地铁线id", dataType="int", paramType = "query", example="0"),
            @ApiImplicitParam(name="name", value="地铁站名称", dataType="string", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType newAStation(@ApiParam(name="id", value = "用户id", required = true) int id,
                                        @ApiParam(name="routeId", value = "地铁线id", required = true) int routeId,
                                        @ApiParam(name="name", value = "地铁站名称", required = true) String name
    ) throws BusinessException {
        JSONObject returnJSON = vStationService.newAStation(id, routeId, name);
        return CommonReturnType.create(returnJSON);
    }

}