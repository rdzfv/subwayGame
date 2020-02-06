package com.xyy.subway.game2d.service.impl;

import com.xyy.subway.game2d.dao.VStationTeamRespository;
import com.xyy.subway.game2d.dao.VStationTeamTypeRespository;
import com.xyy.subway.game2d.entity.VStationTeam;
import com.xyy.subway.game2d.entity.VStationTeamType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.VStationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

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
}
