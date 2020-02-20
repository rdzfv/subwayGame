package com.xyy.subway.game2d.service;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.error.BusinessException;

/**
 * @author xyy
 * @date 2020/2/20 8:52
 * @description
 */
public interface SumService {
    JSONObject addExp(int userId, int exp) throws BusinessException;
    JSONObject addMoney(int userId, int money) throws BusinessException;
}
