package com.xyy.subway.artree.service.impl;

import com.xyy.subway.artree.dao.UserRepository;
import com.xyy.subway.artree.entity.UserInfo;
import com.xyy.subway.artree.error.BusinessException;
import com.xyy.subway.artree.error.EnumBusinessError;
import com.xyy.subway.artree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/25 9:27
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    /**
     * @author xyy
     * @date 2020/1/25 9:29
    */
    @Override
    public UserInfo getUserInfoById(int id) throws BusinessException {
        UserInfo user = userRepository.getByUserId(id);
        if (user == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        return null;
    }
}
