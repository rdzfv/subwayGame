package com.xyy.subway.game2d.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.xyy.subway.game2d.dao.*;
import com.xyy.subway.game2d.entity.*;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.VUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xyy
 * @date 2020/1/25 9:27
 * @description
 */
@Service
@Scope("prototype")
public class VUserServiceImpl implements VUserService {

    @Autowired
    private VuserRepository vUserRepository;
    
   /**
    * @author xyy
    * @date 2020/1/25 11:17
   */
    @Override
    public VUser getVUserInfoById(int id) throws BusinessException {
        VUser user = vUserRepository.getByUserId(id);
        if (user == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        return user;
    }



    /**
     * @author xyy
     * @date 2020/1/27 12:19
    */
    @Override
    public VUser updateUserInfo(VUser vUser) throws BusinessException {
        // 数据库查出待更新对象
        VUser vUserResult = vUserRepository.getByUserId(vUser.getUserId());
        if (vUserResult == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        // 使用hutool BeanUtil进行对象拷贝（忽略null值）
        BeanUtil.copyProperties(vUser, vUserResult, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        VUser vUserInstance = vUserRepository.save(vUserResult);
        return vUserInstance;
    }

    
    
    
    /**
     * @author xyy
     * @date 2020/2/10 13:51
    */
    @Override
    public VUser newAVUserByUserId(int userId) throws BusinessException {
        VUser vUser = new VUser();
        vUser.setUserId(userId);
        System.out.println(vUser.getUserId());
        vUser.setExp(0L);
        vUser.setSatisfactionDegree(100);
        vUser.setSecurity(100);
        vUser.setUncrowedness(100);
        vUser.setCleaness(100);
        vUser.setMoney(2000L);
        vUser.setAvailableWorkers(0);
        vUser.setLevel(1);
        vUser.setVisitorFlowrate(0L);
        vUser.setWorkers(0);

        VUser vUserResult = vUserRepository.save(vUser);

        return vUserResult;
    }


}
