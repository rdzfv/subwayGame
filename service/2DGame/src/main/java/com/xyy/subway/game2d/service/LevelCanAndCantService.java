package com.xyy.subway.game2d.service;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.error.BusinessException;

/**
 * @author xyy
 * @date 2020/2/11 15:46
 * @description
 */
public interface LevelCanAndCantService {

    JSONObject listCanAndCantByLevel(int level) throws BusinessException;
}
