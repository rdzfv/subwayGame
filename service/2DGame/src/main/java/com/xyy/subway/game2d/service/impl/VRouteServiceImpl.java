package com.xyy.subway.game2d.service.impl;

import com.xyy.subway.game2d.dao.VRouteRespository;
import com.xyy.subway.game2d.entity.VRoute;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.VRouteService;
import com.xyy.subway.game2d.service.VUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:00
 * @description
 */
@Service
public class VRouteServiceImpl implements VRouteService {

    @Autowired
    private VRouteRespository vRouteRespository;


    /**
     * @author xyy
     * @date 2020/1/27 14:39
     */
    @Override
    public VRoute getVRouteInfoById(int id) throws BusinessException {
        VRoute vRoute = vRouteRespository.getById(id);
        if (vRoute == null) {
            throw new BusinessException(EnumBusinessError.VROUTE_NOT_EXIST);
        }
        return vRoute;
    }



    /**
     * @author xyy
     * @date 2020/1/28 15:08
    */
    @Override
    public List<VRoute> getVRoutesInfoByUserId(int id) throws BusinessException {
        List<VRoute> vRoutes = vRouteRespository.getAllByUserId(id);
        return vRoutes;
    }
}
