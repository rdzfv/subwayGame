package com.xyy.subway.game2d.controller;

import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VStationStoreType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.VStationStoreService;
import com.xyy.subway.game2d.service.VUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:17
 * @description
 */
@Api(value="VStationStoreController",tags={"虚拟站点商店接口"})
@Controller("/vstationstore")
@RequestMapping("/vstationstore")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VStationStoreController extends BaseController {
    // 引入
    @Autowired
    private VStationStoreService vStationStoreService;

    /**
     * @author xyy
     * @date 2020/1/27 14:47
     */
    @ApiOperation(value="通过id获取虚拟站点商店信息", tags={}, notes="")
    @RequestMapping(value = "/getVStationStoreInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点商店id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getVStationStoreInfoById(@ApiParam(name="id", value = "虚拟站点商店id", required = true) int id) throws BusinessException {
        VStationStore vStationStore = vStationStoreService.getVStationStoreInfoById(id);
        if (vStationStore == null) throw new BusinessException(EnumBusinessError.VSTATIONSTORE_NOT_EXIST);
        return CommonReturnType.create(vStationStore);
    }




    /**
     * @author xyy
     * @date 2020/1/27 14:55
     */
    @ApiOperation(value="通过id获取虚拟站点商店类型信息", tags={}, notes="")
    @RequestMapping(value = "/getVStationStoreTypeInfoById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点商店类型id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getVStationStoreTypeInfoById(@ApiParam(name="id", value = "虚拟站点商店类型id", required = true) int id) throws BusinessException {
        VStationStoreType vStationStoreType = vStationStoreService.getVStationStoreTypeInfoById(id);
        if (vStationStoreType == null) throw new BusinessException(EnumBusinessError.VSTATIONSTORETYPE_NOT_EXIST);
        return CommonReturnType.create(vStationStoreType);
    }



    /**
     * @author xyy
     * @date 2020/1/27 14:55
     */
    @ApiOperation(value="获取全部虚拟站点商店类型信息", tags={}, notes="")
    @RequestMapping(value = "/listAllVStationStoreTypeInfo", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType listAllVStationStoreTypeInfo() throws BusinessException {
        List<VStationStoreType> vStationStoreTypes = vStationStoreService.listAllVStationStoreTypeInfo();
        return CommonReturnType.create(vStationStoreTypes);
    }
}
