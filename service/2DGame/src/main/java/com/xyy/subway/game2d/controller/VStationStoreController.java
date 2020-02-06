package com.xyy.subway.game2d.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.dto.VRouteStationDTO;
import com.xyy.subway.game2d.entity.*;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.*;
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
    @Autowired
    private VUserService vUserService;
    @Autowired
    private VStationService vStationService;
    @Autowired
    private VRouteService vRouteService;
    @Autowired
    private ToolService toolService;

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



    /**
     * @author xyy
     * @date 2020/1/27 14:55
     */
    @ApiOperation(value="建造一个店铺", tags={}, notes="商铺种类中：1表示甜品店，2表示快餐店，3表示服装店，4表示纪念品店")
    @RequestMapping(value = "/postToBuildAStore", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="stationId", value="虚拟站点id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="storeType", value="商铺种类", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="position", value="商铺位置", dataType="int", paramType = "query", example="")
    })
    public CommonReturnType postToBuildAStore(@ApiParam(name="id", value = "用户id", required = true) int id,
                                              @ApiParam(name="stationId", value = "虚拟站点id", required = true) int stationId,
                                              @ApiParam(name="storeType", value = "商铺种类", required = true) int storeType,
                                              @ApiParam(name="position", value = "商铺位置", required = true) int position
    ) throws BusinessException {
        // 通过商铺种类获取商铺种类详细信息
        VStationStoreType vStationStoreType = vStationStoreService.getVStationStoreTypeInfoById(storeType);
        String detail = vStationStoreType.getDetail();
        JSONArray detailArray = JSONArray.parseArray(detail);
        JSONObject detailObject = (JSONObject)detailArray.get(0);
        int unlockedIn = (Integer)detailObject.get("unlockedIn");
        int cost = (Integer)detailObject.get("cost");
        float profit = Float.parseFloat(detailObject.get("profit").toString());
        float maxProfit = Float.parseFloat(detailObject.get("maxProfit").toString());
        long upExp = Long.parseLong(detailObject.get("upExp").toString());
        int unCrowdedness = (Integer)detailObject.get("unCrowdedness");
        int safety = (Integer)detailObject.get("safety");
        int tidy = (Integer)detailObject.get("tidy");
        int worker = (Integer)detailObject.get("worker");
        int visitor = (Integer)detailObject.get("visitor");
        long building_time = Long.parseLong(detailObject.get("building_time").toString());
        String picUrl = (String)detailObject.get("picUrl");


        // 验证当前账户金钱是否充足
        VUser vUser =  vUserService.getVUserInfoById(id);
        Long money = vUser.getMoney();
        if (cost > money) {
            throw new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_MONEY);
        }
        // 验证当前账户小工是否充足
        int availableWorkers = vUser.getAvailableWorkers();
        if (availableWorkers < worker) {
            throw new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_WORKER);
        }
        // 获取当前地铁站信息
        VStation vStation = vStationService.getVStationInfoById(stationId);
        vStation.setSatisfaction(vStation.getSecurity() + safety);
        vStation.setUncrowedness(vStation.getUncrowedness() + unCrowdedness);
        vStation.setCleaness(vStation.getCleaness() + tidy);
        vStation.setSatisfaction((vStation.getSecurity() + vStation.getUncrowedness() + vStation.getCleaness()) / 3);

        vStation.setVisitorFlowrate(vStation.getVisitorFlowrate() + visitor);

        // station信息写入数据库
        vStationService.updateVStationInfo(vStation);

        // store信息写入数据库
        VStationStore vStationStore = new VStationStore();
        vStationStore.setLevel(1);
        vStationStore.setPosition(position);
        vStationStore.setType(storeType);
        vStationStore.setStatus(0);
        vStationStore.setVstationId(stationId);
        vStationStore.setRemainTime(building_time);
        vStationStore.setUrl(picUrl);
        vStationStore.setAvailableProfit(0f);
        vStationStore.setMaxProfit(maxProfit);
        vStationStore.setIsDeleted(0);
        vStationStore.setIsLevelup(0);

        VStationStore vStationStoreResult = vStationStoreService.postAStore(vStationStore);

        // 根据userId获取全部地铁站信息
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
                int stationIdId = Integer.parseInt(stationIds[j]);
                VStation vStationInstance = vStationService.getVStationInfoById(stationIdId);
                String stationName = vStationInstance.getName();
                vRouteStationDTO.setUserId(id);
                vRouteStationDTO.setRouteId(routeId);
                vRouteStationDTO.setRouteName(routeName);
                vRouteStationDTO.setRouteStationId(stationId);
                vRouteStationDTO.setRouteStationName(stationName);
                vRouteStationDTOS.add(vRouteStationDTO);
            }
        }

        // 计算用户的总指数
        int cleanSum = 0;
        int safeSum = 0;
        int uncrowdedSum = 0;
        for (int i = 0; i < vRouteStationDTOS.size(); i ++) {
            int aStationId = vRouteStationDTOS.get(i).getRouteStationId();
            VStation vStationInstance = vStationService.getVStationInfoById(aStationId);
            cleanSum = cleanSum + vStationInstance.getCleaness();
            safeSum = safeSum + vStationInstance.getSecurity();
            uncrowdedSum = uncrowdedSum + vStationInstance.getUncrowedness();
        }
        cleanSum = cleanSum / vRouteStationDTOS.size();
        safeSum = safeSum / vRouteStationDTOS.size();
        uncrowdedSum = uncrowdedSum / vRouteStationDTOS.size();
        int satisfiyed = (cleanSum + safeSum + uncrowdedSum) / 3;

        // 更新用户信息
        vUser.setCleaness(cleanSum);
        vUser.setSecurity(safeSum);
        vUser.setUncrowedness(uncrowdedSum);
        vUser.setSatisfactionDegree(satisfiyed);
        vUser.setAvailableWorkers(vUser.getWorkers() - worker);
        vUser.setExp(vUser.getExp() + upExp);
        vUser.setMoney(vUser.getMoney() - cost);
        vUser.setVisitorFlowrate(vUser.getVisitorFlowrate() + visitor);

        // 判断是否升级了
        int isUpLevel = 0;
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel(vUser.getExp());
        if (expAndLevelDTO.getLevel() != vUser.getLevel()) {
            vUser.setLevel(expAndLevelDTO.getLevel());
            isUpLevel = 1;
        }

        // 把更新后的用户信息写入数据库
        vUserService.updateUserInfo(vUser);

        // 构造返回对象
        JSONObject object = new JSONObject();
        object.put("newStore", vStationStoreResult);
        object.put("isUpLevel", isUpLevel);
        object.put("newUser", vUser);
        object.put("isSurprise", 0);
        object.put("levelDetail", expAndLevelDTO);
        object.put("storeTypeDetail", detailObject);

        // 开始建筑计时
        int storeId = vStationStore.getId();
        toolService.xyyBuildingTimer(building_time, vStationStoreResult.getId(), worker, id, vStationStoreService); //创建线程任务时，直接将所需依赖注入的bean携带进子线程中

        // 开始金币计时
        toolService.xyyMoneyTimer(vStationStoreResult.getId(), id, profit, maxProfit, vStationStoreService);

        return CommonReturnType.create(object);
    }




    /**
     * @author xyy
     * @date 2020/2/1 10:34
    */
    @ApiOperation(value="升级店铺", tags={}, notes="商铺种类中：1表示甜品店，2表示快餐店，3表示服装店，4表示纪念品店")
    @RequestMapping(value = "/updateStore", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="storeId", value="商店id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="stationId", value="虚拟站点id", dataType="int", paramType = "query", example="")
    })
    public CommonReturnType postToBuildAStore(@ApiParam(name="id", value = "用户id", required = true) int id,
                                              @ApiParam(name="stationId", value = "虚拟站点id", required = true) int stationId,
                                              @ApiParam(name="storeId", value = "商店id", required = true) int storeId
    ) throws BusinessException {
        // 通过商店id获取商店信息
        VStationStore vStationStore = vStationStoreService.getVStationStoreInfoById(storeId);
        int preLevel = vStationStore.getLevel();
        int storeType = vStationStore.getType();
        // 通过商铺种类获取商铺种类详细信息
        VStationStoreType vStationStoreType = vStationStoreService.getVStationStoreTypeInfoById(storeType);
        String detail = vStationStoreType.getDetail();
        JSONArray detailArray = JSONArray.parseArray(detail);
        JSONObject detailObject = (JSONObject)detailArray.get(preLevel);
        int unlockedIn = (Integer)detailObject.get("unlockedIn");
        int cost = (Integer)detailObject.get("cost");
        float profit = Float.parseFloat(detailObject.get("profit").toString());
        float maxProfit = Float.parseFloat(detailObject.get("maxProfit").toString());
        long upExp = Long.parseLong(detailObject.get("upExp").toString());
        int unCrowdedness = (Integer)detailObject.get("unCrowdedness");
        int safety = (Integer)detailObject.get("safety");
        int tidy = (Integer)detailObject.get("tidy");
        int worker = (Integer)detailObject.get("worker");
        int visitor = (Integer)detailObject.get("visitor");
        long building_time = Long.parseLong(detailObject.get("building_time").toString());
        String picUrl = (String)detailObject.get("picUrl");


        // 验证当前账户金钱是否充足
        VUser vUser =  vUserService.getVUserInfoById(id);
        Long money = vUser.getMoney();
        if (cost > money) {
            throw new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_MONEY);
        }
        // 验证当前账户小工是否充足
        int availableWorkers = vUser.getAvailableWorkers();
        if (availableWorkers < worker) {
            throw new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_WORKER);
        }
        // 获取当前地铁站信息
        VStation vStation = vStationService.getVStationInfoById(stationId);
        vStation.setSatisfaction(vStation.getSecurity() + safety);
        vStation.setUncrowedness(vStation.getUncrowedness() + unCrowdedness);
        vStation.setCleaness(vStation.getCleaness() + tidy);
        vStation.setSatisfaction((vStation.getSecurity() + vStation.getUncrowedness() + vStation.getCleaness()) / 3);

        vStation.setVisitorFlowrate(vStation.getVisitorFlowrate() + visitor);

        // station信息写入数据库
        vStationService.updateVStationInfo(vStation);

        // store信息写入数据库
        vStationStore.setLevel(preLevel + 1);
        vStationStore.setStatus(0);
        vStationStore.setRemainTime(building_time);
        vStationStore.setUrl(picUrl);
        vStationStore.setMaxProfit(maxProfit);
        vStationStore.setIsLevelup(1);
        VStationStore vStationStoreResult = vStationStoreService.postAStore(vStationStore);

        // 根据userId获取全部地铁站信息
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
                int stationIdId = Integer.parseInt(stationIds[j]);
                VStation vStationInstance = vStationService.getVStationInfoById(stationIdId);
                String stationName = vStationInstance.getName();
                vRouteStationDTO.setUserId(id);
                vRouteStationDTO.setRouteId(routeId);
                vRouteStationDTO.setRouteName(routeName);
                vRouteStationDTO.setRouteStationId(stationId);
                vRouteStationDTO.setRouteStationName(stationName);
                vRouteStationDTOS.add(vRouteStationDTO);
            }
        }

        // 计算用户的总指数
        int cleanSum = 0;
        int safeSum = 0;
        int uncrowdedSum = 0;
        for (int i = 0; i < vRouteStationDTOS.size(); i ++) {
            int aStationId = vRouteStationDTOS.get(i).getRouteStationId();
            VStation vStationInstance = vStationService.getVStationInfoById(aStationId);
            cleanSum = cleanSum + vStationInstance.getCleaness();
            safeSum = safeSum + vStationInstance.getSecurity();
            uncrowdedSum = uncrowdedSum + vStationInstance.getUncrowedness();
        }
        cleanSum = cleanSum / vRouteStationDTOS.size();
        safeSum = safeSum / vRouteStationDTOS.size();
        uncrowdedSum = uncrowdedSum / vRouteStationDTOS.size();
        int satisfiyed = (cleanSum + safeSum + uncrowdedSum) / 3;

        // 更新用户信息
        vUser.setCleaness(cleanSum);
        vUser.setSecurity(safeSum);
        vUser.setUncrowedness(uncrowdedSum);
        vUser.setSatisfactionDegree(satisfiyed);
        vUser.setAvailableWorkers(vUser.getWorkers() - worker);
        vUser.setExp(vUser.getExp() + upExp);
        vUser.setMoney(vUser.getMoney() - cost);
        vUser.setVisitorFlowrate(vUser.getVisitorFlowrate() + visitor);

        // 判断是否升级了
        int isUpLevel = 0;
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel(vUser.getExp());
        if (expAndLevelDTO.getLevel() != vUser.getLevel()) {
            vUser.setLevel(expAndLevelDTO.getLevel());
            isUpLevel = 1;
        }

        // 把更新后的用户信息写入数据库
        vUserService.updateUserInfo(vUser);

        // 构造返回对象
        JSONObject object = new JSONObject();
        object.put("newStore", vStationStoreResult);
        object.put("isUpLevel", isUpLevel);
        object.put("newUser", vUser);
        object.put("isSurprise", 0);
        object.put("levelDetail", expAndLevelDTO);
        object.put("storeTypeDetail", detailObject);

        // 开始建筑计时
        toolService.xyyBuildingTimer(building_time, storeId, worker, id, vStationStoreService); //创建线程任务时，直接将所需依赖注入的bean携带进子线程中

        // 开始金币计时
        toolService.xyyMoneyTimer(storeId, id, profit, maxProfit, vStationStoreService);

        return CommonReturnType.create(object);
    }





    /**
     * @author xyy
     * @date 2020/2/1 11:39
    */
    @ApiOperation(value="收取金币", tags={}, notes="")
    @RequestMapping(value = "/harvestMoney", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="storeId", value="商店id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="money", value="收取金币数量", dataType="int", paramType = "query", example=""),
    })
    @ResponseBody
    public CommonReturnType harvestMoney(@ApiParam(name="userId", value = "用户id", required = true) int userId,
                                         @ApiParam(name="storeId", value = "商店id", required = true) int storeId,
                                         @ApiParam(name="money", value = "收取金币数量", required = true) float money
    ) throws BusinessException {
        // 更新店铺已赚取金币
        VStationStore vStationStore = vStationStoreService.getVStationStoreInfoById(storeId);
        if (vStationStore == null) throw new BusinessException(EnumBusinessError.VSTATIONSTORE_NOT_EXIST);
        vStationStore.setAvailableProfit(vStationStore.getAvailableProfit() - money);
        vStationStoreService.updateStationStoreInfo(vStationStore);

        // 更新用户金币数
        VUser vUser = vUserService.getVUserInfoById(userId);
        vUser.setMoney(vUser.getMoney() + (long)money);
        vUserService.updateUserInfo(vUser);


        // 构造返回对象
        JSONObject object = new JSONObject();
        object.put("newStore", vStationStore);
        object.put("newUser", vUser);
        object.put("isSurprise", 0);

        return CommonReturnType.create(object);
    }




    /**
     * @author xyy
     * @date 2020/2/5 10:08
     */
    @ApiOperation(value="移动商铺", tags={}, notes="")
    @RequestMapping(value = "/moveStore", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点商店id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="newPos", value="新的位置", dataType="int", paramType = "query", example=""),
    })
    @ResponseBody
    public CommonReturnType moveStore(@ApiParam(name="id", value = "虚拟站点商店id", required = true) int id,
                                      @ApiParam(name="newPos", value = "新的位置", required = true) int newPos
    ) throws BusinessException {
        VStationStore vStationStore = vStationStoreService.moveStore(id, newPos);
        if (vStationStore == null) throw new BusinessException(EnumBusinessError.VSTATIONSTORE_NOT_EXIST);
        return CommonReturnType.create(vStationStore);
    }




    /**
     * @author xyy
     * @date 2020/2/5 10:30
    */
    @ApiOperation(value="删除商铺", tags={}, notes="")
    @RequestMapping(value = "/removeStore", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="虚拟站点商店id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType removeStore(@ApiParam(name="id", value = "虚拟站点商店id", required = true) int id) throws BusinessException {
        VStationStore vStationStore = vStationStoreService.removeStore(id);
        if (vStationStore == null) throw new BusinessException(EnumBusinessError.VSTATIONSTORE_NOT_EXIST);
        return CommonReturnType.create(vStationStore);
    }
}
