package com.xyy.subway.game2d.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.xyy.subway.game2d.dao.VStationRespository;
import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.VStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/28 12:08
 * @description
 */
@Service
public class VStationServiceImpl implements VStationService {
    @Autowired
    private VStationRespository vStationRespository;

    /**
     * @author xyy
     * @date 2020/1/27 13:49
     */
    @Override
    public VStation updateVStationInfo(VStation vStation) throws BusinessException {
        // 数据库查出待更新对象
        VStation vStationResult = vStationRespository.getById(vStation.getId());
        if (vStationResult == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        // 使用hutool BeanUtil进行对象拷贝（忽略null值）
        BeanUtil.copyProperties(vStation, vStationResult, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        VStation vStationInstance = vStationRespository.save(vStationResult);
        return vStationInstance;
    }


    /**
     * @author xyy
     * @date 2020/1/27 14:15
     */
    @Override
    public VStation getVStationInfoById(int id) throws BusinessException {
        VStation vStation = vStationRespository.getById(id);
        if (vStation == null) {
            throw new BusinessException(EnumBusinessError.VSTATION_NOT_EXIST);
        }
        return vStation;
    }
}
