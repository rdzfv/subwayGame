package com.xyy.subway.game2d.runnable;

import com.xyy.subway.game2d.entity.VStationStore;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.service.VStationStoreService;
import com.xyy.subway.game2d.service.impl.VStationStoreSerivceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/1/31 21:02
 * @description
 */
@Data
@Service
public class XyyTimerRunnable implements Runnable {

    private VStationStoreService vStationStoreService;
    private long time;
    private Integer stationId;

    @Override
    public void run(){
        boolean exit = true;
        try {
            while(exit) {
                Thread.sleep(10000);
                time = time - 10000;
                System.out.println(time);
                if(time > 0) {
                    // 更新建造的剩余时间
                    VStationStore vStationStore = new VStationStore();
                    try {
                        vStationStore = vStationStoreService.getVStationStoreInfoById(stationId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    vStationStore.setRemainTime(time);
                    try {
                        vStationStoreService.updateStationStoreInfo(vStationStore);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    exit = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
