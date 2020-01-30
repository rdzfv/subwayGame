package com.xyy.subway.game2d.service;

import com.xyy.subway.game2d.entity.VRoute;
import com.xyy.subway.game2d.error.BusinessException;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 11:58
 * @description
 */
public interface VRouteService {
    VRoute getVRouteInfoById(int id) throws BusinessException;
    List<VRoute> getVRoutesInfoByUserId(int id) throws BusinessException;
}
