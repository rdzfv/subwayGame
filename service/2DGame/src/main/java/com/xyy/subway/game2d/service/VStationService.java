package com.xyy.subway.game2d.service;

import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.error.BusinessException;

/**
 * @author xyy
 * @date 2020/1/28 12:07
 * @description
 */
public interface VStationService {
    VStation updateVStationInfo(VStation vStation) throws BusinessException;
    VStation getVStationInfoById(int id) throws BusinessException;
}
