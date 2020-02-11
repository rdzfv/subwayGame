package com.xyy.subway.game2d.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dao.VStationTeamRespository;
import com.xyy.subway.game2d.dao.VStationTeamTypeRespository;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.dto.VBuildTeamTypeDetailDTO;
import com.xyy.subway.game2d.entity.VStationTeam;
import com.xyy.subway.game2d.entity.VStationTeamType;
import com.xyy.subway.game2d.entity.VUser;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.ToolService;
import com.xyy.subway.game2d.service.VStationTeamService;
import com.xyy.subway.game2d.service.VUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/28 12:21
 * @description
 */
@Service
@Scope("prototype")
public class VStationTeamServiceImpl implements VStationTeamService {

    @Autowired
    private VStationTeamRespository vStationTeamRespository;
    @Autowired
    private VStationTeamTypeRespository vStationTeamTypeRespository;
    @Autowired
    private ToolService toolService;
    @Autowired
    private VUserService vUserService;


    /**
     * @author xyy
     * @date 2020/1/27 14:48
     */
    @Override
    public VStationTeam getVStationTeamInfoById(int id) throws BusinessException {
        VStationTeam vStationTeam = vStationTeamRespository.getById(id);
        if (vStationTeam == null) {
            throw new BusinessException(EnumBusinessError.VSTATIONTEAM_NOT_EXIST);
        }
        return vStationTeam;
    }



    /**
     * @author xyy
     * @date 2020/1/27 19:33
     */
    @Override
    public VStationTeamType getVStationTeamTypeInfoById(int id) throws BusinessException {
        VStationTeamType vStationTeamType = vStationTeamTypeRespository.getById(id);
        if (vStationTeamType == null) {
            throw new BusinessException(EnumBusinessError.VSTATIONTEAMTYPE_NOT_EXIST);
        }
        return vStationTeamType;
    }




    /**
     * @author xyy
     * @date 2020/2/10 19:13
    */
    @Override
    public JSONObject newABuildingTeam(int id, int stationId, int level) throws BusinessException {

        VStationTeamType vStationTeamType = getVStationTeamTypeInfoById(1);
        String detail = vStationTeamType.getDetail();
        ArrayList<VBuildTeamTypeDetailDTO> vBuildTeamTypeDetailDTOS1 = new ArrayList<>();

        // 字符串转换为JSON数组
        JSONArray detailArray = JSONArray.parseArray(detail);

        for (int i = 0; i < detailArray.size(); i++) {
            JSONObject detailObject = (JSONObject)detailArray.get(i);

            String name = (String)detailObject.get("name");
            int level1 = (Integer)detailObject.get("level");
            int unlockedIn = (Integer)detailObject.get("unlockedIn");
            long cost = Long.parseLong(detailObject.get("cost").toString());
            long upExp = Long.parseLong(detailObject.get("upExp").toString());
            long visitor = Long.parseLong(detailObject.get("visitor").toString());
            int increaseWorker = (Integer)detailObject.get("increaseWorker");
            String picUrl = (String)detailObject.get("picUrl");

            VBuildTeamTypeDetailDTO vBuildTeamTypeDetailDTO = new VBuildTeamTypeDetailDTO();
            vBuildTeamTypeDetailDTO.setName(name);
            vBuildTeamTypeDetailDTO.setLevel(level1);
            vBuildTeamTypeDetailDTO.setUnlockedIn(unlockedIn);
            vBuildTeamTypeDetailDTO.setCost(cost);
            vBuildTeamTypeDetailDTO.setUpExp(upExp);
            vBuildTeamTypeDetailDTO.setVisitor(visitor);
            vBuildTeamTypeDetailDTO.setIncreaseWorker(increaseWorker);
            vBuildTeamTypeDetailDTO.setPicUrl(picUrl);

            vBuildTeamTypeDetailDTOS1.add(vBuildTeamTypeDetailDTO);
        }

        VBuildTeamTypeDetailDTO vBuildTeamTypeDetailDTO = vBuildTeamTypeDetailDTOS1.get(level - 1);

        VUser vUser = vUserService.getVUserInfoById(id);

        // 校验金币并扣除
        if (vUser.getMoney() < vBuildTeamTypeDetailDTO.getCost()) {
            throw new BusinessException(EnumBusinessError.USER_NOT_ENOUGH_MONEY);
        }
        vUser.setMoney(vUser.getMoney() - vBuildTeamTypeDetailDTO.getCost());

        // 增加经验并进行升级判断
        vUser.setExp(vUser.getExp() + vBuildTeamTypeDetailDTO.getUpExp());
        int preLevel = vUser.getLevel();
        int isUpLevel = 0;
        ExpAndLevelDTO expAndLevelDTO = toolService.calculateExpAndLevel(vUser.getExp());
        if (expAndLevelDTO.getLevel() != preLevel) {
            // 升级了
            isUpLevel = 1;
        }

        // 增加小工人数
        vUser.setWorkers(vUser.getWorkers() + vBuildTeamTypeDetailDTO.getIncreaseWorker());
        vUser.setAvailableWorkers(vUser.getAvailableWorkers() + vBuildTeamTypeDetailDTO.getIncreaseWorker());

        // 增加人流量
        vUser.setVisitorFlowrate(vUser.getVisitorFlowrate() + vBuildTeamTypeDetailDTO.getVisitor());

        // 构造返回对象
        JSONObject object = new JSONObject();
        object.put("isUpLevel", isUpLevel);
        object.put("newUser", vUser);
        object.put("isSurprise", 0);
        object.put("levelDetail", expAndLevelDTO);

        return object;
    }
}
