package com.xyy.subway.game2d.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dao.DetailRespository;
import com.xyy.subway.game2d.dto.StationBuildDetailDTO;
import com.xyy.subway.game2d.dto.StoreUpLevelUpDetailDTO;
import com.xyy.subway.game2d.dto.VBuildTeamTypeDetailDTO;
import com.xyy.subway.game2d.dto.VTeamTypeDetailDTO;
import com.xyy.subway.game2d.entity.VStationStoreType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/11 15:46
 * @description
 */
@Service
public class LevelCanAndCantServiceImpl implements LevelCanAndCantService {
    @Autowired
    private TeamDetailService teamDetailService;
    @Autowired
    private VStationStoreService vStationStoreService;
    @Autowired
    private ToolService toolService;


    /**
     * @author xyy
     * @date 2020/2/9 15:08
     */
    @Override
    public JSONObject listCanAndCantByLevel(int level) throws BusinessException {
        // 构造返回对象
        JSONObject object = new JSONObject();
        // 查询店铺升级策略
        for (int i = 1; i <= 4; i++) {
            VStationStoreType vStationStoreType = vStationStoreService.getVStationStoreTypeInfoById(i);
            String name = vStationStoreType.getName();

            List<StoreUpLevelUpDetailDTO> storeUpLevelUpDetailDTOS = toolService.checkStoreUpLevelUpDetail(i);
            for (int j = 0; j < storeUpLevelUpDetailDTOS.size(); j++) {
                int unlockedIn = storeUpLevelUpDetailDTOS.get(j).getUnlockedIn();
                object.put(name + (j + 1), level >= unlockedIn ? 1 : 0);
            }
        }

        // 查询队伍升级策略
        List<VBuildTeamTypeDetailDTO> vBuildTeamTypeDetailDTOS = teamDetailService.checkBuildingTeamDetail(1);
        for (int j = 0; j < vBuildTeamTypeDetailDTOS.size(); j++) {
            int unlockedIn = vBuildTeamTypeDetailDTOS.get(j).getUnlockedIn();
            String name = vBuildTeamTypeDetailDTOS.get(j).getName();
            object.put(name + (j + 1), level >= unlockedIn ? 1 : 0);
        }
        for (int i = 2; i <= 4; i++) {
            List<VTeamTypeDetailDTO> vTeamTypeDetailDTOS = teamDetailService.checkOtherBuildingTeamDetail(i);
            for (int j = 0; j < vTeamTypeDetailDTOS.size(); j++) {
                int unlockedIn = vTeamTypeDetailDTOS.get(j).getUnlockedIn();
                String name = vTeamTypeDetailDTOS.get(j).getName();
                object.put(name + (j + 1), level >= unlockedIn ? 1 : 0);
            }
        }

        // 查询地铁站升级策略
        List<StationBuildDetailDTO> stationBuildDetailDTOS = toolService.checkStationBuildDetail(1);
        for(int i = 0; i < stationBuildDetailDTOS.size(); i++) {
            if ("a".equals(stationBuildDetailDTOS.get(i).getUnLockedIn())) {
                object.put("station" + (i + 1), "新用户赠送");
                continue;
            }
            if ("b".equals(stationBuildDetailDTOS.get(i).getUnLockedIn())) {
                object.put("station" + (i + 1), "完成新手教程获得");
                continue;
            }
            int unLockedIn = Integer.parseInt(stationBuildDetailDTOS.get(i).getUnLockedIn());
            object.put("station" + (i + 1), level >= unLockedIn ? 1 : 0);
        }

        return object;
    }
}
