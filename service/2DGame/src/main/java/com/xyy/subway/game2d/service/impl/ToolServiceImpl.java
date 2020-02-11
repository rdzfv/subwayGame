package com.xyy.subway.game2d.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dao.DetailRespository;
import com.xyy.subway.game2d.dto.*;
import com.xyy.subway.game2d.entity.*;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.runnable.XyyBuildingTimerRunnable;
import com.xyy.subway.game2d.runnable.XyyMoneyTimerRunnable;
import com.xyy.subway.game2d.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/31 14:16
 * @description
 */
@Service
@Scope("prototype")
public class ToolServiceImpl implements ToolService {
    @Autowired
    private VStationStoreService vStationStoreService;
    @Autowired
    private VUserService vUserService;
    @Autowired
    private DetailRespository detailRespository;


    @Override
    public ExpAndLevelDTO calculateExpAndLevel(Long exp) {
        if (exp < 50) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(1);
            expAndLevelDTO.setNextLevelExp(50L);
            return expAndLevelDTO;
        } else if (exp < 200) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(2);
            expAndLevelDTO.setNextLevelExp(200L);
            return expAndLevelDTO;
        } else if (exp < 500) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(3);
            expAndLevelDTO.setNextLevelExp(500L);
            return expAndLevelDTO;
        } else if (exp < 1000) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(4);
            expAndLevelDTO.setNextLevelExp(1000L);
            return expAndLevelDTO;
        } else if (exp < 2000) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(5);
            expAndLevelDTO.setNextLevelExp(2000L);
            return expAndLevelDTO;
        } else if (exp < 9999000) {
            ExpAndLevelDTO expAndLevelDTO = new ExpAndLevelDTO();
            expAndLevelDTO.setExp(exp);
            expAndLevelDTO.setLevel(6);
            expAndLevelDTO.setNextLevelExp(3000L);
            return expAndLevelDTO;
        }

        return null;
    }




    /**
     * @author xyy
     * @date 2020/1/31 20:12
    */
    @Override
    @Async
    public void xyyBuildingTimer(long time, int storeId, int workers, int userId, VStationStoreService vStationStoreService) throws BusinessException {
        XyyBuildingTimerRunnable xyyBuildingTimerRunnable = new XyyBuildingTimerRunnable();
        xyyBuildingTimerRunnable.setStationId(storeId);
        xyyBuildingTimerRunnable.setTime(time);
        xyyBuildingTimerRunnable.setVStationStoreService(vStationStoreService);

        xyyBuildingTimerRunnable.run();

        // 更新店铺状态
        VStationStore vStationStore = vStationStoreService.getVStationStoreInfoById(storeId);
        vStationStore.setStatus(1);
        vStationStoreService.updateStationStoreInfo(vStationStore);

        // 更新用户信息（可用小工数量）
        VUser vUser = vUserService.getVUserInfoById(userId);
        vUser.setAvailableWorkers(vUser.getAvailableWorkers() + workers);
        vUserService.updateUserInfo(vUser);

        // 更新剩余时间
        VStationStore vStationStoreInstance = vStationStoreService.getVStationStoreInfoById(storeId);
        vStationStoreInstance.setRemainTime(0L);
        vStationStoreService.updateStationStoreInfo(vStationStoreInstance);
    }




    /**
     * @author xyy
     * @date 2020/2/9 14:41
    */
    @Override
    public boolean ifEnoughMoney(int id, long cost) {
        VUser vUser = new VUser();
        try {
            vUser = vUserService.getVUserInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Long money = vUser.getMoney();
        if (cost > money) {
            return false;
        }
        return true;
    }



    /**
     * @author xyy
     * @date 2020/2/9 14:49
    */
    @Override
    public boolean ifEnoughWorker(int id, int costWorker) {
        VUser vUser = new VUser();
        try {
            vUser = vUserService.getVUserInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int availableWorkers = vUser.getAvailableWorkers();
        if (availableWorkers < costWorker) {
            return false;
        }
        return true;
    }




    /**
     * @author xyy
     * @date 2020/2/9 15:29
    */
    @Override
    public List<StoreUpLevelUpDetailDTO> checkStoreUpLevelUpDetail(int storeType) {
        VStationStoreType vStationStoreType = new VStationStoreType();
        ArrayList<StoreUpLevelUpDetailDTO> storeUpLevelUpDetailDTOS = new ArrayList<>();
        try {
            vStationStoreType = vStationStoreService.getVStationStoreTypeInfoById(storeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String detail = vStationStoreType.getDetail();
        // 字符串转换为JSON数组
        JSONArray detailArray = JSONArray.parseArray(detail);
        for (int i = 0; i < detailArray.size(); i++) {
            JSONObject detailObject = (JSONObject)detailArray.get(i);
            StoreUpLevelUpDetailDTO storeUpLevelUpDetailDTO = new StoreUpLevelUpDetailDTO();

            int unlockedIn = (Integer)detailObject.get("unlockedIn");
            int cost = (Integer)detailObject.get("cost");
            float profit = Float.parseFloat(detailObject.get("profit").toString());
            float maxProfit = Float.parseFloat(detailObject.get("maxProfit").toString());
            long upExp = Long.parseLong(detailObject.get("upExp").toString());
            int unCrowdedness = (Integer)detailObject.get("unCrowdedness");
            int safety = (Integer)detailObject.get("safety");
            int tidy = (Integer)detailObject.get("tidy");
            int worker = (Integer)detailObject.get("worker");
            int visitor = (Integer)detailObject.get("visitor");
            long building_time = Long.parseLong(detailObject.get("building_time").toString());
            String picUrl = (String)detailObject.get("picUrl");

            storeUpLevelUpDetailDTO.setUnlockedIn(unlockedIn);
            storeUpLevelUpDetailDTO.setCost(cost);
            storeUpLevelUpDetailDTO.setProfit(profit);
            storeUpLevelUpDetailDTO.setMaxProfit(maxProfit);
            storeUpLevelUpDetailDTO.setUpExp(upExp);
            storeUpLevelUpDetailDTO.setUnCrowdedness(unCrowdedness);
            storeUpLevelUpDetailDTO.setSafety(safety);
            storeUpLevelUpDetailDTO.setTidy(tidy);
            storeUpLevelUpDetailDTO.setWorker(worker);
            storeUpLevelUpDetailDTO.setVisitor(visitor);
            storeUpLevelUpDetailDTO.setBuilding_time(building_time);
            storeUpLevelUpDetailDTO.setPicUrl(picUrl);

            storeUpLevelUpDetailDTOS.add(storeUpLevelUpDetailDTO);
        }

        return storeUpLevelUpDetailDTOS;
    }




    /**
     * @author xyy
     * @date 2020/2/9 16:40
    */
    @Override
    public List<StationBuildDetailDTO> checkStationBuildDetail(int id) {
        Detail detail = detailRespository.getById(id);
        String detailDetail = detail.getDetail();
        ArrayList<StationBuildDetailDTO> stationBuildDetailDTOS = new ArrayList<>();

        // 字符串转换为JSON数组
        JSONArray detailArray = JSONArray.parseArray(detailDetail);

        for (int i = 0; i < detailArray.size(); i++) {
            JSONObject detailObject = (JSONObject)detailArray.get(i);
            StationBuildDetailDTO storeUpLevelUpDetailDTO = new StationBuildDetailDTO();

            int idid = (Integer)detailObject.get("id");
            long cost = Long.parseLong(detailObject.get("cost").toString());
            String unLockedIn = (String)detailObject.get("unLockedIn");
            long exp = Long.parseLong(detailObject.get("exp").toString());

            storeUpLevelUpDetailDTO.setId(idid);
            storeUpLevelUpDetailDTO.setCost(cost);
            storeUpLevelUpDetailDTO.setUnLockedIn(unLockedIn);
            storeUpLevelUpDetailDTO.setExp(exp);

            stationBuildDetailDTOS.add(storeUpLevelUpDetailDTO);
        }
        return stationBuildDetailDTOS;
    }
}
