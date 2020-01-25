package com.xyy.subway.artree.service;

import com.xyy.subway.artree.entity.VUser;
import com.xyy.subway.artree.error.BusinessException;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/25 9:21
 * @description
 */
public interface VUserService {
    VUser getVUserInfoById(int id) throws BusinessException;
}
