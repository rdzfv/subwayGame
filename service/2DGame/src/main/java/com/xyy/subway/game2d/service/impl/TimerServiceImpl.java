package com.xyy.subway.game2d.service.impl;

import com.xyy.subway.game2d.dao.DetailRespository;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.runnable.XyyMoneyTimerRunnable;
import com.xyy.subway.game2d.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xyy
 * @date 2020/2/10 23:03
 * @description
 */
@Service
@Scope("prototype")
public class TimerServiceImpl implements TimerService {
    @Autowired
    private VStationStoreService vStationStoreService;
    @Autowired
    private VUserService vUserService;
    @Autowired
    private VStationService vStationService;
    @Autowired
    private VRouteService vRouteService;

    /**
     * @author xyy
     * @date 2020/2/1 11:19
     */
    @Override
    public void xyyMoneyTimer() throws BusinessException {
        System.out.println("进入xyyMoneyTimer");
        XyyMoneyTimerRunnable xyyMoneyTimerRunnable = new XyyMoneyTimerRunnable();

        xyyMoneyTimerRunnable.setVStationStoreService(vStationStoreService);
        xyyMoneyTimerRunnable.setVRouteService(vRouteService);
        xyyMoneyTimerRunnable.setVStationService(vStationService);
        xyyMoneyTimerRunnable.setVUserService(vUserService);

        xyyMoneyTimerRunnable.run();

    }
}
