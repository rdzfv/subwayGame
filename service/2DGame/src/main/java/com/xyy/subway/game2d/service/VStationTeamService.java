package com.xyy.subway.game2d.service;

import com.xyy.subway.game2d.entity.VStationTeam;
import com.xyy.subway.game2d.entity.VStationTeamType;
import com.xyy.subway.game2d.error.BusinessException;

/**
 * @author xyy
 * @date 2020/1/28 12:21
 * @description
 */
public interface VStationTeamService {
    VStationTeam getVStationTeamInfoById(int id) throws BusinessException;
    VStationTeamType getVStationTeamTypeInfoById(int id) throws BusinessException;
}
