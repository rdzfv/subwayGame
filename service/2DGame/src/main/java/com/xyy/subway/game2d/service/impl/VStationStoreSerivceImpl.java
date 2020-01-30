package com.xyy.subway.game2d.service.impl;

import com.xyy.subway.game2d.dao.VStationStoreRespository;
import com.xyy.subway.game2d.dao.VStationStoreTypeRespository;
import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VStationStoreType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.VStationStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:14
 * @description
 */
@Service
public class VStationStoreSerivceImpl implements VStationStoreService {

    @Autowired
    private VStationStoreRespository vStationStoreRespository;
    @Autowired
    private VStationStoreTypeRespository vStationStoreTypeRespository;

    /**
     * @author xyy
     * @date 2020/1/27 14:48
     */
    @Override
    public VStationStore getVStationStoreInfoById(int id) throws BusinessException {
        VStationStore vStationStore = vStationStoreRespository.getById(id);
        if (vStationStore == null) {
            throw new BusinessException(EnumBusinessError.VSTATIONSTORE_NOT_EXIST);
        }
        return vStationStore;
    }



    /**
     * @author xyy
     * @date 2020/1/27 14:58
     */
    @Override
    public VStationStoreType getVStationStoreTypeInfoById(int id) throws BusinessException {
        VStationStoreType vStationStoreType = vStationStoreTypeRespository.getById(id);
        if (vStationStoreType == null) {
            throw new BusinessException(EnumBusinessError.VSTATIONSTORETYPE_NOT_EXIST);
        }
        return vStationStoreType;
    }



    /**
     * @author xyy
     * @date 2020/1/28 16:06
    */
    @Override
    public List<VStationStore> getVStationStoreInfoByStationId(int id) throws BusinessException {
        List<VStationStore> vStationStores = vStationStoreRespository.getAllByVstationId(id);
        return vStationStores;
    }

    @Override
    public List<VStationStoreType> listAllVStationStoreTypeInfo() {
        List<VStationStoreType> vStationStoreTypes =vStationStoreTypeRespository.findAll();
        return vStationStoreTypes;
    }
}
