package com.xyy.subway.game2d.runnable;

import com.xyy.subway.game2d.entity.VRoute;
import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.entity.VUser;
import com.xyy.subway.game2d.service.VRouteService;
import com.xyy.subway.game2d.service.VStationService;
import com.xyy.subway.game2d.service.VStationStoreService;
import com.xyy.subway.game2d.service.VUserService;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyy
 * @date 2020/2/1 11:01
 * @description
 */
@Data
@Service
public class XyyMoneyTimerRunnable implements Runnable {

    private VStationStoreService vStationStoreService;
    private VStationService vStationService;
    private VRouteService vRouteService;
    private VUserService vUserService;

    @Override
    public void run() {
        // 读出所有的站点
        try {
            while (true) {
                System.out.println("我在执行哦");
                Thread.sleep(10000);

                System.out.println("vStationStoreService" + vStationStoreService);
                List<VStationStore> vStationStores = vStationStoreService.getVStationStoreInfo();

                if (vStationStores == null) {
                    continue;
                }
                int size = vStationStores.size();
                for (int i = 0; i < size; i++) {
                    VStationStore vStationStore = vStationStores.get(i);
                    if (vStationStore == null) continue;

                    // 辗转获取userId
                    int stationId = vStationStore.getVstationId();
                    VStation vStation = vStationService.getVStationInfoById(stationId);
                    int routeId = Integer.parseInt(vStation.getVrouteIds());
                    VRoute vRoute = vRouteService.getVRouteInfoById(routeId);
                    int userId = vRoute.getUserId();

                    // 获取用户的信息
                    VUser vUser = vUserService.getVUserInfoById(userId);
                    int satisfaction = vUser.getSatisfactionDegree();

                    // 如果还在建造中就跳过
                    if (vStationStore.getStatus() == 0) {
                        continue;
                    }

                    // 如果已经删除了就跳过
                    if (vStationStore.getIsDeleted() == 1) {
                        continue;
                    }

                    int level = vStationStore.getLevel();
                    System.out.println(level);

                    float up = 0;
                    if (satisfaction < 60) { // 较低满意度
                        up = 1F;
                    } else if (satisfaction < 90) { // 普通满意度
                        if (level == 1) {
                            up = 1F;
                        } else if (level == 2) {
                            up = 4F;
                        } else if (level == 3) {
                            up = 10F;
                        }
                    } else { // 较高满意度
                        if (level == 1) {
                            up = 1F * 1.2f;
                        } else if (level == 2) {
                            up = 4F * 1.2f;
                        } else if (level == 3) {
                            up = 10F * 1.2f;
                        }
                    }


                    float avail = vStationStore.getAvailableProfit() + up;
                    if (avail < vStationStore.getMaxProfit()) {
                        vStationStore.setAvailableProfit(avail);
                        vStationStoreService.postAStore(vStationStore);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}