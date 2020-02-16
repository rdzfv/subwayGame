package com.xyy.subway.game2d.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dao.VStationTeamRespository;
import com.xyy.subway.game2d.dao.VStationTeamTypeRespository;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.dto.VBuildTeamTypeDetailDTO;
import com.xyy.subway.game2d.dto.VRouteStationDTO;
import com.xyy.subway.game2d.dto.VTeamTypeDetailDTO;
import com.xyy.subway.game2d.entity.*;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:21
 * @description
 */
@Service
@Scope("prototype")
public class VStationTeamServiceImpl implements VStationTeamService {

    @Autowired
    private VStationTeamRespository vStationTeamRespository;
    @Autowired
    private VStationTeamTypeRespository vStationTeamTypeRespository;
    @Autowired
    private ToolService toolService;
    @Autowired
    private VUserService vUserService;
    @Autowired
    private VStationService vStationService;
    @Autowired
    private VRouteService vRouteService;


    /**
     * @author xyy
     * @date 2020/1/27 14:48
     */
    @Override
    public VStationTeam getVStationTeamInfoById(int id) throws BusinessException {
        VStationTeam vStationTeam = vStationTeamRespository.getById(id);
        if (vStationTeam == null) {
            throw new BusinessException(EnumBusinessError.VSTATIONTEAM_NOT_EXIST);
        }
        return vStationTeam;
    }



    /**
     * @author xyy
     * @date 2020/1/27 19:33
     */
    @Override
    public VStationTeamType getVStationTeamTypeInfoById(int id) throws BusinessException {
        VStationTeamType vStationTeamType = vStationTeamTypeRespository.getById(id);
        if (vStationTeamType == null) {
            throw new BusinessException(EnumBusinessError.VSTATIONTEAMTYPE_NOT_EXIST);
        }
        return vStationTeamType;
    }




    /**
     * @author xyy
     * @date 2020/2/10 19:13
    */
    @Override
    public JSONObject newABuildingTeam(int id, int stationId, int level) throws BusinessException {

        VStationTeamType vStationTeamType = getVStationTeamTypeInfoById(1);
        String detail = vStationTeamType.getDetail();
        ArrayList<VBuildTeamTypeDetailDTO> vBuildTeamTypeDetailDTOS1 = new ArrayList<>();

        // 字符串转换为JSON数组
        JSONArray detailArray = JSONArray.parseArray(detail);

        for (int i = 0; i < detailArray.size(); i++) {
            JSONObject detailObject = (JSONObject)detailArray.get(i);

            String name = (String)detailObject.get("name");
            int level1 = (Integer)detailObject.get("level");
            int unlockedIn = (Integer)detailObject.get("unlockedIn");
            long cost = Long.parseLong(detailObject.get("cost").toString());
            long upExp = Long.parseLong(detailObject.get("upExp").toString());
            long visitor = Long.parseLong(detailObject.get("visitor").toString());
            int increaseWorker = (Integer)detailObject.get("increaseWorker");
            String picUrl = (String)detailObject.get("picUrl");

            VBuildTeamTypeDetailDTO vBuildTeamTypeDetailDTO = new VBuildTeamTypeDetailDTO();
            vBuildTeamTypeDetailDTO.setName(name);
            vBuildTeamTypeDetailDTO.setLevel(level1);
            vBuildTeamTypeDetailDTO.setUnlockedIn(unlockedIn);
            vBuildTeamTypeDetailDTO.setCost(cost);
            vBuildTeamTypeDetailDTO.setUpExp(upExp);
            vBuildTeamTypeDetailDTO.setVisitor(visitor);
            vBuildTeamTypeDetailDTO.setIncreaseWorker(increaseWorker);
            vBuildTeamTypeDetailDTO.setPicUrl(picUrl);

            vBuildTeamTypeDetailDTOS1.add(vBuildTeamTypeDetailDTO);
        }

        VBuildTeamTypeDetailDTO vBuildTeamTypeDetailDTO = vBuildTeamTypeDetailDTOS1.get(level - 1);

        VUser vUser = vUserService.getVUserInfoById(id);

        // 校验金币并扣除
        if (vUser.getMoney() < vBuildTeamTypeDetailDTO.getCost()) {
            throw new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_MONEY);
        }
        vUser.setMoney(vUser.getMoney() - vBuildTeamTypeDetailDTO.getCost());

        // 增加经验并进行升级判断
        vUser.setExp(vUser.getExp() + vBuildTeamTypeDetailDTO.getUpExp());
        int preLevel = vUser.getLevel();
        int isUpLevel = 0;
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel(vUser.getExp());
        if (expAndLevelDTO.getLevel() != preLevel) {
            // 升级了
            isUpLevel = 1;
        }

        // 增加小工人数
        vUser.setWorkers(vUser.getWorkers() + vBuildTeamTypeDetailDTO.getIncreaseWorker());
        vUser.setAvailableWorkers(vUser.getAvailableWorkers() + vBuildTeamTypeDetailDTO.getIncreaseWorker());

        // 增加人流量
        vUser.setVisitorFlowrate(vUser.getVisitorFlowrate() + vBuildTeamTypeDetailDTO.getVisitor());

        vUserService.updateUserInfo(vUser);

        // 构造返回对象
        JSONObject object = new JSONObject();
        object.put("isUpLevel", isUpLevel);
        object.put("newUser", vUser);
        object.put("isSurprise", 0);
        object.put("levelDetail", expAndLevelDTO);

        return object;
    }

    
    
    
    
    /**
     * @author xyy
     * @date 2020/2/11 13:27
    */
    @Override
    public JSONObject newATeam(int id, int stationId, int level, int type) throws BusinessException {

        VStationTeamType vStationTeamType = getVStationTeamTypeInfoById(type);
        String detail = vStationTeamType.getDetail();
        ArrayList<VTeamTypeDetailDTO> vTeamTypeDetailDTOS = new ArrayList<>();

        // 字符串转换为JSON数组
        JSONArray detailArray = JSONArray.parseArray(detail);
        System.out.println(1);

        for (int i = 0; i < detailArray.size(); i++) {
            JSONObject detailObject = (JSONObject)detailArray.get(i);


            System.out.println(1);
            String name = (String)detailObject.get("name");
            System.out.println(1);
            int level1 = (Integer)detailObject.get("level");
            System.out.println(1);
            int unlockedIn = (Integer)detailObject.get("unlockedIn");
            System.out.println(1);
            long cost = Long.parseLong(detailObject.get("cost").toString());
            System.out.println(1);
            long upExp = Long.parseLong(detailObject.get("upExp").toString());
            System.out.println(1);
            long visitor = Long.parseLong(detailObject.get("visitor").toString());
            System.out.println(1);
            int increaseTidy = (Integer)detailObject.get("increaseTidy");
            System.out.println(1);
            int increaseUncrowded = (Integer)detailObject.get("increaseUncrowded");
            System.out.println(1);
            int increaseSafe = (Integer)detailObject.get("increaseSafe");
            System.out.println(1);
            String picUrl = (String)detailObject.get("picUrl");
            System.out.println(1);

            VTeamTypeDetailDTO vBuildTeamTypeDetailDTO = new VTeamTypeDetailDTO();
            vBuildTeamTypeDetailDTO.setName(name);
            vBuildTeamTypeDetailDTO.setLevel(level1);
            vBuildTeamTypeDetailDTO.setUnlockedIn(unlockedIn);
            vBuildTeamTypeDetailDTO.setCost(cost);
            vBuildTeamTypeDetailDTO.setUpExp(upExp);
            vBuildTeamTypeDetailDTO.setVisitor(visitor);
            vBuildTeamTypeDetailDTO.setIncreaseSafe(increaseSafe);
            vBuildTeamTypeDetailDTO.setIncreaseTidy(increaseTidy);
            vBuildTeamTypeDetailDTO.setIncreaseUncrowded(increaseUncrowded);
            vBuildTeamTypeDetailDTO.setPicUrl(picUrl);

            vTeamTypeDetailDTOS.add(vBuildTeamTypeDetailDTO);
        }

        VTeamTypeDetailDTO vTeamTypeDetailDTO = vTeamTypeDetailDTOS.get(level - 1);

        VUser vUser = vUserService.getVUserInfoById(id);

        // 校验金币并扣除
        if (vUser.getMoney() < vTeamTypeDetailDTO.getCost()) {
            throw new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_MONEY);
        }
        vUser.setMoney(vUser.getMoney() - vTeamTypeDetailDTO.getCost());

        // 增加经验并进行升级判断
        vUser.setExp(vUser.getExp() + vTeamTypeDetailDTO.getUpExp());
        int preLevel = vUser.getLevel();
        int isUpLevel = 0;
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel(vUser.getExp());
        if (expAndLevelDTO.getLevel() != preLevel) {
            // 升级了
            isUpLevel = 1;
        }


        // 读出对应地铁站，修改各类指数
        VStation vStation = vStationService.getVStationInfoById(stationId);
        vStation.setSecurity(vStation.getSecurity() + vTeamTypeDetailDTO.getIncreaseSafe());
        vStation.setCleaness(vStation.getCleaness() + vTeamTypeDetailDTO.getIncreaseTidy());
        vStation.setUncrowedness(vStation.getUncrowedness() + vTeamTypeDetailDTO.getIncreaseUncrowded());
        vStation.setSatisfaction((vStation.getCleaness() + vStation.getUncrowedness() + vStation.getSecurity()) / 3);
        vStationService.updateVStationInfo(vStation);


        // 根据userId获取全部地铁站信息
        List<VRoute> vRoutes = vRouteService.getVRoutesInfoByUserId(id);
        ArrayList<VRouteStationDTO> vRouteStationDTOS = new ArrayList<>();
        if (vRoutes == null) {
            return null;
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

        // 更新用户指数
        vUser.setCleaness(cleanSum);
        vUser.setSecurity(safeSum);
        vUser.setUncrowedness(uncrowdedSum);
        vUser.setSatisfactionDegree(satisfiyed);

        // 增加人流量
        vUser.setVisitorFlowrate(vUser.getVisitorFlowrate() + vTeamTypeDetailDTO.getVisitor());

        vUserService.updateUserInfo(vUser);

        // 构造返回对象
        JSONObject object = new JSONObject();
        object.put("isUpLevel", isUpLevel);
        object.put("newUser", vUser);
        object.put("isSurprise", 0);
        object.put("levelDetail", expAndLevelDTO);

        return object;
    }





    /**
     * @author xyy
     * @date 2020/2/15 19:49
    */
    @Override
    public VStationTeamType updateBuildingTeamTypeDetail(String detail) throws BusinessException {
        VStationTeamType vStationTeamType = new VStationTeamType();
        vStationTeamType.setId(1);
        vStationTeamType.setName("建筑团队");
        vStationTeamType.setDetail(detail);

        VStationTeamType vStationTeamTypeResult = vStationTeamTypeRespository.save(vStationTeamType);
        return vStationTeamTypeResult;
    }






    /**
     * @author xyy
     * @date 2020/2/16 10:52
    */
    @Override
    public VStationTeamType updateOtherTeamTypeDetail(String detail, int type) throws BusinessException {
        VStationTeamType vStationTeamType = new VStationTeamType();
        vStationTeamType.setId(type);
        if (type == 2) {
            vStationTeamType.setName("清洁团队");
        } else if (type == 3) {
            vStationTeamType.setName("服务团队");
        } else if (type == 4) {
            vStationTeamType.setName("安保团队");
        }
        vStationTeamType.setDetail(detail);

        VStationTeamType vStationTeamTypeResult = vStationTeamTypeRespository.save(vStationTeamType);
        return vStationTeamTypeResult;
    }
}
