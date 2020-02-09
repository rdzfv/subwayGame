package com.xyy.subway.game2d.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dao.VRouteRespository;
import com.xyy.subway.game2d.dao.VStationRespository;
import com.xyy.subway.game2d.dao.VuserRepository;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.dto.StationBuildDetailDTO;
import com.xyy.subway.game2d.entity.VRoute;
import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.entity.VUser;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.ToolService;
import com.xyy.subway.game2d.service.VRouteService;
import com.xyy.subway.game2d.service.VStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:08
 * @description
 */
@Service
@Scope("prototype")
public class VStationServiceImpl implements VStationService {
    @Autowired
    private VStationRespository vStationRespository;
    @Autowired
    private VRouteRespository vRouteRespository;
    @Autowired
    private VuserRepository vuserRepository;
    @Autowired
    private ToolService toolService;
    @Autowired
    private VRouteService vRouteService;

    /**
     * @author xyy
     * @date 2020/1/27 13:49
     */
    @Override
    public VStation updateVStationInfo(VStation vStation) throws BusinessException {
        // 数据库查出待更新对象
        VStation vStationResult = vStationRespository.getById(vStation.getId());
        if (vStationResult == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        // 使用hutool BeanUtil进行对象拷贝（忽略null值）
        BeanUtil.copyProperties(vStation, vStationResult, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        VStation vStationInstance = vStationRespository.save(vStationResult);
        return vStationInstance;
    }


    /**
     * @author xyy
     * @date 2020/1/27 14:15
     */
    @Override
    public VStation getVStationInfoById(int id) throws BusinessException {
        VStation vStation = vStationRespository.getById(id);
        if (vStation == null) {
            throw new BusinessException(EnumBusinessError.VSTATION_NOT_EXIST);
        }
        return vStation;
    }



    /**
     * @author xyy
     * @date 2020/2/2 15:58
    */
    @Override
    public JSONObject newAStation(int id, int routeId, String name, int type) throws BusinessException {
        List<StationBuildDetailDTO> stationBuildDetailDTOS = toolService.checkStationBuildDetail(1);
        StationBuildDetailDTO stationBuildDetailDTO = stationBuildDetailDTOS.get(type - 1);
        String unLockedIn = stationBuildDetailDTO.getUnLockedIn();
        float cost = stationBuildDetailDTO.getCost();
        long exp = stationBuildDetailDTO.getExp();

        VUser vUser = vuserRepository.getByUserId(id);

        // 情景校验
        switch (type) {
            // 新用户赠送
            case 1: {
                // 新建一条地铁线
                VRoute vRoute = vRouteService.newAVRoute(id);
                routeId = vRoute.getId();
                break;
            }

            // 完成新手引导
            case 2: {
                break;
            }

            default: {
                // 查询用户判断金币是否足够
                if (vUser.getMoney() < cost) {
                    throw  new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_MONEY);
                }
                // 经验增加
                vUser.setExp(vUser.getExp() + exp);
                // 金币减少
                vUser.setMoney((long)(vUser.getMoney() - cost));
            }


        }

        // 新建station并写入数据库
        VStation vStation = new VStation();
        vStation.setName(name);
        vStation.setVrouteIds(Integer.toString(routeId));
        vStation.setUncrowedness(100);
        vStation.setVisitorFlowrate(0L);
        vStation.setSatisfaction(100);
        vStation.setCleaness(100);
        vStation.setSecurity(100);
        VStation vStationResult = vStationRespository.save(vStation);
        int newStationId = vStationResult.getId();

        // 查询路线添加上新的地铁站并写回数据库
        VRoute vRoute = vRouteRespository.getById(routeId);
        String preStations = vRoute.getVstationIds();
        if ("".equals(preStations) || preStations == null) {
            preStations = newStationId + "";
        } else {
            preStations = preStations + "," + newStationId;
        }
        String[] stations = preStations.split(",");
        int stationNum = stations.length;
        vRoute.setVstationIds(preStations);
        vRouteRespository.save(vRoute);

        // 修改用户的各项指数并写回数据库
        long expexp = vUser.getExp();
        System.out.println(1);
        int level = vUser.getLevel();
        System.out.println(1);
        vUser.setCleaness(vUser.getCleaness() / stationNum);
        System.out.println(1);
        vUser.setUncrowedness(vUser.getUncrowedness() / stationNum);
        System.out.println(1);
        vUser.setSecurity(vUser.getSecurity() / stationNum);
        System.out.println(1);
        vUser.setSatisfactionDegree((vUser.getCleaness() + vUser.getSecurity() + vUser.getUncrowedness()) / 3);
        System.out.println(1);
        vuserRepository.save(vUser);

        System.out.println(1);

        // 判断是否升级
        int isUpLevel = 0;
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel(expexp);
        if (expAndLevelDTO.getLevel() != level) {
            isUpLevel = 1;
        }


        // 构造返回对象
        JSONObject object = new JSONObject();
        object.put("newStation", vStationResult);
        object.put("isUpLevel", isUpLevel);
        object.put("newUser", vUser);
        object.put("isSurprise", 0);
        object.put("levelDetail", expAndLevelDTO);

        return object;
    }
}
