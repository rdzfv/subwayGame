package com.xyy.subway.trueword.service;

import com.xyy.subway.trueword.entity.UserInfo;
import com.xyy.subway.trueword.error.BusinessException;
import com.xyy.subway.trueword.model.UserInfoDetail;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/25 9:21
 * @description
 */
@Service
public interface UserService {
    UserInfo getUserInfoById(int id) throws BusinessException;
    UserInfo login(String openId);
    UserInfoDetail updateUserInfo(UserInfoDetail userInfoDetail);
}
