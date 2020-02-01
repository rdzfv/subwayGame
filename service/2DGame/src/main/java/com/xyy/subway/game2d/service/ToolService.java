package com.xyy.subway.game2d.service;

import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.error.BusinessException;

/**
 * @author xyy
 * @date 2020/1/31 14:04
 * @description
 */
public interface ToolService {
    ExpAndLevelDTO calculateExpAndLevel(Long exp);
    void xyyBuildingTimer(long time, int storeId, int workers, int userId, VStationStoreService vStationStoreService) throws BusinessException;
    void xyyMoneyTimer(int storeId, int userId, float profit, float maxProfit, VStationStoreService vStationStoreService) throws BusinessException;
}
