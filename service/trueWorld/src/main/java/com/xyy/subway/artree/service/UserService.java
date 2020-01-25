package com.xyy.subway.artree.service;

import com.xyy.subway.artree.entity.UserInfo;
import com.xyy.subway.artree.error.BusinessException;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/25 9:21
 * @description
 */
@Service
public interface UserService {
    UserInfo getUserInfoById(int id) throws BusinessException;
}
