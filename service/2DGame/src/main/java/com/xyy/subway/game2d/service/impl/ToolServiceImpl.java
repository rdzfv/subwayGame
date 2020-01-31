package com.xyy.subway.game2d.service.impl;

import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VUser;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.runnable.XyyTimerRunnable;
import com.xyy.subway.game2d.service.ToolService;
import com.xyy.subway.game2d.service.VStationStoreService;
import com.xyy.subway.game2d.service.VUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/31 14:16
 * @description
 */
@Service
public class ToolServiceImpl implements ToolService {
    @Autowired
    private VStationStoreService vStationStoreService;
    @Autowired
    private VUserService vUserService;

    @Override
    public ExpAndLevelDTO calculateExpAndLevel(Long exp) {
        if (exp < 50) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(1);
            expAndLevelDTO.setNextLevelExp(50L);
            return expAndLevelDTO;
        } else if (exp < 200) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(2);
            expAndLevelDTO.setNextLevelExp(200L);
            return expAndLevelDTO;
        } else if (exp < 500) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(3);
            expAndLevelDTO.setNextLevelExp(500L);
            return expAndLevelDTO;
        } else if (exp < 1000) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(4);
            expAndLevelDTO.setNextLevelExp(1000L);
            return expAndLevelDTO;
        } else if (exp < 2000) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(5);
            expAndLevelDTO.setNextLevelExp(2000L);
            return expAndLevelDTO;
        } else if (exp < 3000) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(6);
            expAndLevelDTO.setNextLevelExp(3000L);
            return expAndLevelDTO;
        }

        return null;
    }




    /**
     * @author xyy
     * @date 2020/1/31 20:12
    */
    @Override
    @Async
    public void xyyTimer(long time, int storeId, int workers, int userId, VStationStoreService vStationStoreService) throws BusinessException {
        XyyTimerRunnable xyyTimerRunnable = new XyyTimerRunnable();
        xyyTimerRunnable.setStationId(storeId);
        xyyTimerRunnable.setTime(time);
        xyyTimerRunnable.setVStationStoreService(vStationStoreService);

        xyyTimerRunnable.run();

        // 更新店铺状态
        VStationStore vStationStore = vStationStoreService.getVStationStoreInfoById(storeId);
        vStationStore.setStatus(1);
        vStationStoreService.updateStationStoreInfo(vStationStore);

        // 更新用户信息（可用小工数量）
        VUser vUser = vUserService.getVUserInfoById(userId);
        vUser.setAvailableWorkers(vUser.getAvailableWorkers() + workers);
        vUserService.updateUserInfo(vUser);

        // 更新剩余时间
        VStationStore vStationStoreInstance = vStationStoreService.getVStationStoreInfoById(storeId);
        vStationStoreInstance.setRemainTime(0L);
        vStationStoreService.updateStationStoreInfo(vStationStoreInstance);
    }
}
