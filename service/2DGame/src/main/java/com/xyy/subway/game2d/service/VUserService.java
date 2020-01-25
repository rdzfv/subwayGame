package com.xyy.subway.game2d.service;


import com.xyy.subway.game2d.entity.VUser;
import com.xyy.subway.game2d.error.BusinessException;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/25 9:21
 * @description
 */
public interface VUserService {
    VUser getVUserInfoById(int id) throws BusinessException;
}
