package com.xyy.subway.game2d.service;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.dto.StationBuildDetailDTO;
import com.xyy.subway.game2d.dto.StoreUpLevelUpDetailDTO;
import com.xyy.subway.game2d.dto.VBuildTeamTypeDetailDTO;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/31 14:04
 * @description
 */
public interface ToolService {
    ExpAndLevelDTO calculateExpAndLevel(Long exp);
    void xyyBuildingTimer(long time, int storeId, int workers, int userId, VStationStoreService vStationStoreService) throws BusinessException;
    boolean ifEnoughMoney(int id, long cost);
    boolean ifEnoughWorker(int id, int costWorker);
    JSONObject listCanAndCantByLevel(int level) throws BusinessException;
    List<StoreUpLevelUpDetailDTO> checkStoreUpLevelUpDetail(int storeType);
    List<StationBuildDetailDTO> checkStationBuildDetail(int id);
    List<VBuildTeamTypeDetailDTO> checkBuildingTeamDetail(int defaultId) throws BusinessException;
}
