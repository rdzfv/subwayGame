package com.xyy.subway.game2d.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.xyy.subway.game2d.dao.VStationStoreRespository;
import com.xyy.subway.game2d.dao.VStationStoreTypeRespository;
import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VStationStoreType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.VStationStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:14
 * @description
 */
@Service
@Scope("prototype")
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



    /**
     * @author xyy
     * @date 2020/1/30 16:00
    */
    @Override
    public List<VStationStoreType> listAllVStationStoreTypeInfo() {
        List<VStationStoreType> vStationStoreTypes =vStationStoreTypeRespository.findAll();
        return vStationStoreTypes;
    }



    /**
     * @author xyy
     * @date 2020/1/31 16:25
    */
    @Override
    public VStationStore postAStore(VStationStore vStationStore) throws BusinessException {
        VStationStore vStationStoreInstance = vStationStoreRespository.save(vStationStore);
        return vStationStoreInstance;
    }



    /**
     * @author xyy
     * @date 2020/1/31 20:20
    */
    @Override
    public VStationStore updateStationStoreInfo(VStationStore vStationStore) throws BusinessException {
        vStationStoreRespository.save(vStationStore);
        return null;
    }




    /**
     * @author xyy
     * @date 2020/2/5 10:13
    */
    @Override
    public VStationStore moveStore(int id, int newPos) throws BusinessException {
        // 数据库查出待更新对象
        VStationStore vStationStore = vStationStoreRespository.getById(id);
        vStationStore.setPosition(newPos);
        VStationStore vStationStoreResult = vStationStoreRespository.save(vStationStore);
        return vStationStoreResult;
    }
}
