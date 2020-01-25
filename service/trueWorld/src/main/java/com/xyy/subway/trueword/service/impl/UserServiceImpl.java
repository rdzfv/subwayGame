package com.xyy.subway.trueword.service.impl;

import com.xyy.subway.trueword.dao.UserRepository;
import com.xyy.subway.trueword.entity.UserInfo;
import com.xyy.subway.trueword.error.BusinessException;
import com.xyy.subway.trueword.error.EnumBusinessError;
import com.xyy.subway.trueword.model.UserInfoDetail;
import com.xyy.subway.trueword.service.UserService;
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
        return user;
    }


    /**
     * @author xyy
     * @date 2020/1/25 13:50
    */
    @Override
    public UserInfo login(String openId) {
        // 检查该用户是否已经存在
        UserInfo user = userRepository.getByOpenId(openId);
        return user;
    }



    /**
     * @author xyy
     * @date 2020/1/25 14:29
    */
    @Override
    public UserInfoDetail updateUserInfo(UserInfoDetail userInfoDetail) {
        // model转换entity
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userInfoDetail.getName());
        userInfo.setIcon_url(userInfoDetail.getIcon_url());
        userInfo.setOpenId(userInfoDetail.getOpenId());
        // 更新用户信息
        UserInfo userInfoResult = userRepository.save(userInfo);
        // entity转model
        UserInfoDetail userInfoDetailResult = new UserInfoDetail();
        userInfoDetailResult.setUserId(userInfoResult.getUserId());
        userInfoDetailResult.setName(userInfoResult.getName());
        userInfoDetailResult.setIcon_url(userInfoResult.getIcon_url());
        userInfoDetailResult.setFriend_ids(userInfoResult.getFriend_ids());
        userInfoDetailResult.setCreateTime(userInfoResult.getCreateTime());
        userInfoDetailResult.setModifyTime(userInfoResult.getModifyTime());
        return userInfoDetail;
    }
}
