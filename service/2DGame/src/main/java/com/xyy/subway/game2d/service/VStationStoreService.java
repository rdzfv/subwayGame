package com.xyy.subway.game2d.service;

import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VStationStoreType;
import com.xyy.subway.game2d.error.BusinessException;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:13
 * @description
 */
public interface VStationStoreService {
    VStationStore getVStationStoreInfoById(int id) throws BusinessException;
    VStationStoreType getVStationStoreTypeInfoById(int id) throws BusinessException;
    List<VStationStore> getVStationStoreInfoByStationId(int id) throws BusinessException;
    List<VStationStoreType> listAllVStationStoreTypeInfo();
    boolean postAStore(VStationStore vStationStore) throws BusinessException;
    VStationStore updateStationStoreInfo(VStationStore vStationStore) throws BusinessException;
}
