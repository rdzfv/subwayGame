package com.xyy.subway.game2d.runnable;

import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VUser;
import com.xyy.subway.game2d.service.VStationStoreService;
import com.xyy.subway.game2d.service.VUserService;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/2/1 11:01
 * @description
 */
@Data
@Service
@Scope("prototype")
public class XyyMoneyTimerRunnable implements Runnable {

    private VStationStoreService vStationStoreService;
    private float profit;
    private float maxProfit;
    private int storeId;
    private float availableProfit;

    @Override
    public void run(){
        boolean exit = false;

        // 读出建造状态,直到建造完成开始金币计算
        VStationStore vStationStore = new VStationStore();
        while (true) {
            try {
                vStationStore = vStationStoreService.getVStationStoreInfoById(storeId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int status = vStationStore.getStatus();
            if (status == 1) {
                exit = true;
                break;
            }
        }


        try {
            VStationStore vStationStoreResult = new VStationStore();
            while(exit) {
                Thread.sleep(10000);
                // 读出数据库中available金币数量
                try {
                    vStationStore = vStationStoreService.getVStationStoreInfoById(storeId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                availableProfit = vStationStore.getAvailableProfit();
                availableProfit = availableProfit + profit * 10;
                System.out.println(availableProfit);
                if(availableProfit <= maxProfit) {
                    // 更新金币数量
                    try {
                        vStationStore = vStationStoreService.getVStationStoreInfoById(storeId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vStationStore.setAvailableProfit(availableProfit);
                    try {
                        vStationStoreService.updateStationStoreInfo(vStationStore);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // 查询店铺是否已被删除
                try {
                    vStationStoreResult = vStationStoreService.getVStationStoreInfoById(storeId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int isDeleted = vStationStoreResult.getIsDeleted();
                if (isDeleted == 1) {
                    exit = false;
                }

                // 查询店铺是否有升级操作
                int isLevelUp = vStationStoreResult.getIsLevelup();
                if (isLevelUp == 1) {
                    exit = false;
                }
                // 再将升级操作写回0
                vStationStore.setIsLevelup(0);
                try {
                    vStationStoreService.updateStationStoreInfo(vStationStore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}