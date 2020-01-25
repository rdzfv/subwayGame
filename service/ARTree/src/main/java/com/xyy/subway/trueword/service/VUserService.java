package com.xyy.subway.trueword.service;

import com.xyy.subway.trueword.entity.VUser;
import com.xyy.subway.trueword.error.BusinessException;

/**
 * @author xyy
 * @date 2020/1/25 9:21
 * @description
 */
public interface VUserService {
    VUser getVUserInfoById(int id) throws BusinessException;
}
