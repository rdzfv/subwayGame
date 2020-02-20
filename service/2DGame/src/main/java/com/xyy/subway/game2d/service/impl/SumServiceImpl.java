package com.xyy.subway.game2d.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dao.VuserRepository;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.entity.VUser;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.service.SumService;
import com.xyy.subway.game2d.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/2/20 8:58
 * @description
 */
@Service
public class SumServiceImpl implements SumService {

    @Autowired
    private VuserRepository vUserRepository;
    @Autowired
    private ToolService toolService;

    /**
     * @author xyy
     * @date 2020/2/19 21:28
     */
    @Override
    public JSONObject addExp(int userId, int exp) throws BusinessException {
        VUser vUser = vUserRepository.getByUserId(userId);
        vUser.setExp(vUser.getExp() + exp);
        System.out.println("userAfter: " + vUser.getExp());
        int isLevelUp = 0;

        // 判断是否升级
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel((long)exp);
        if (expAndLevelDTO.getLevel().equals(vUser.getLevel())) {
            isLevelUp = 1;
            vUser.setLevel(expAndLevelDTO.getLevel());
        }
        vUser = vUserRepository.save(vUser);

        JSONObject object = new JSONObject();
        object.put("vUser", vUser);
        object.put("isLevelUp", isLevelUp);

        return object;
    }

    
    
    
    
    
    /**
     * @author xyy
     * @date 2020/2/20 9:54
    */
    @Override
    public JSONObject addMoney(int userId, int money) throws BusinessException {
        VUser vUser = vUserRepository.getByUserId(userId);
        vUser.setMoney(vUser.getMoney() + money);
        vUser = vUserRepository.save(vUser);

        JSONObject object = new JSONObject();
        object.put("vUser", vUser);

        return object;
    }
}
