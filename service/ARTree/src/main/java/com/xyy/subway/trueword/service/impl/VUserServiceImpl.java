package com.xyy.subway.trueword.service.impl;

import com.xyy.subway.trueword.dao.VuserRepository;
import com.xyy.subway.trueword.entity.VUser;
import com.xyy.subway.trueword.error.BusinessException;
import com.xyy.subway.trueword.error.EnumBusinessError;
import com.xyy.subway.trueword.service.VUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/25 9:27
 * @description
 */
@Service
public class VUserServiceImpl implements VUserService {

    @Autowired
    private VuserRepository VUserRepository;
    
   /**
    * @author xyy
    * @date 2020/1/25 11:50
   */
    @Override
    public VUser getVUserInfoById(int id) throws BusinessException {
        VUser user = VUserRepository.getByUserId(id);
        if (user == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        return null;
    }
}
