package com.xyy.subway.game2d.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dto.VBuildTeamTypeDetailDTO;
import com.xyy.subway.game2d.dto.VTeamTypeDetailDTO;
import com.xyy.subway.game2d.entity.VStationTeamType;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.service.TeamDetailService;
import com.xyy.subway.game2d.service.VStationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyy
 * @date 2020/2/11 15:40
 * @description
 */
@Service
public class TeamDetailServiceImpl implements TeamDetailService {
    @Autowired
    private VStationTeamService vStationTeamService;

    /**
     * @author xyy
     * @date 2020/2/11 15:10
     */
    @Override
    public List<VTeamTypeDetailDTO> checkOtherBuildingTeamDetail(int type) throws BusinessException {
        VStationTeamType vStationTeamType = vStationTeamService.getVStationTeamTypeInfoById(type);
        String detail = vStationTeamType.getDetail();
        ArrayList<VTeamTypeDetailDTO> vTeamTypeDetailDTOS = new ArrayList<>();

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
            int increaseTidy = (Integer)detailObject.get("increaseTidy");
            int increaseUncrowded = (Integer)detailObject.get("increaseUncrowded");
            int increaseSafe = (Integer)detailObject.get("increaseSafe");
            String picUrl = (String)detailObject.get("picUrl");

            VTeamTypeDetailDTO vBuildTeamTypeDetailDTO = new VTeamTypeDetailDTO();
            vBuildTeamTypeDetailDTO.setName(name);
            vBuildTeamTypeDetailDTO.setLevel(level1);
            vBuildTeamTypeDetailDTO.setUnlockedIn(unlockedIn);
            vBuildTeamTypeDetailDTO.setCost(cost);
            vBuildTeamTypeDetailDTO.setUpExp(upExp);
            vBuildTeamTypeDetailDTO.setVisitor(visitor);
            vBuildTeamTypeDetailDTO.setIncreaseSafe(increaseSafe);
            vBuildTeamTypeDetailDTO.setIncreaseTidy(increaseTidy);
            vBuildTeamTypeDetailDTO.setIncreaseUncrowded(increaseUncrowded);
            vBuildTeamTypeDetailDTO.setPicUrl(picUrl);

            vTeamTypeDetailDTOS.add(vBuildTeamTypeDetailDTO);
        }
        return vTeamTypeDetailDTOS;
    }






    /**
     * @author xyy
     * @date 2020/2/11 15:23
     */
    @Override
    public List<VBuildTeamTypeDetailDTO> checkBuildingTeamDetail(int defaultId) throws BusinessException {
        VStationTeamType vStationTeamType = vStationTeamService.getVStationTeamTypeInfoById(1);
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
        return vBuildTeamTypeDetailDTOS1;
    }
}
