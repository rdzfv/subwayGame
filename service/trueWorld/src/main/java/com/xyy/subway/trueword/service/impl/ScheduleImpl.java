package com.xyy.subway.trueword.service.impl;



import com.xyy.subway.trueword.entity.UserInfo;
import com.xyy.subway.trueword.dao.*;
import com.xyy.subway.trueword.entity.*;
import com.xyy.subway.trueword.entity.view.UserScheduleView;
import com.xyy.subway.trueword.error.BusinessException;
import com.xyy.subway.trueword.error.EnumBusinessError;
import com.xyy.subway.trueword.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/26 11:30
 * @description
 */
@Service
public class ScheduleImpl implements ScheduleService {

    @Autowired
    private UserScheduleRepository userScheduleRepository;
    @Autowired
    private CityRespository cityRespository;
    @Autowired
    private RouteRespository routeRespository;
    @Autowired
    private StationRespository stationRespository;
    @Autowired
    private TrainScheduleRepository trainScheduleRepository;
    @Autowired
    private UserScheduleViewRespository userScheduleViewRespository;


    /**
     * @author xyy
     * @date 2020/1/26 11:31
    */
    @Override
    public UserSchedule getScheduleById(int id) throws BusinessException {
        UserSchedule userSchedule = userScheduleRepository.getById(id);
        return userSchedule;
    }



    /**
     * @author xyy
     * @date 2020/1/26 11:55
    */
    @Override
    public City getCityById(int id) throws BusinessException {
        City city = cityRespository.getById(id);
        return city;
    }



    /**
     * @author xyy
     * @date 2020/1/26 12:10
    */
    @Override
    public Route getRouteById(int id) throws BusinessException {
        Route route = routeRespository.getById(id);
        return route;
    }



    /**
     * @author xyy
     * @date 2020/1/26 12:10
     */
    @Override
    public Station getStationById(String id) throws BusinessException {
        Station station = stationRespository.getById(id);
        return station;
    }



    /**
     * @author xyy
     * @date 2020/1/26 13:21
    */
    @Override
    public TrainSchedule getTrainScheduleById(int id) throws BusinessException {
        TrainSchedule trainSchedule = trainScheduleRepository.getById(id);
        return trainSchedule;
    }



    /**
     * @author xyy
     * @date 2020/1/26 15:36
    */
    @Override
    public UserScheduleView getUserScheduleById(int id) throws BusinessException {
        UserScheduleView userScheduleView = userScheduleViewRespository.getById(id);
        return userScheduleView;
    }

    
    
    
    /**
     * @author xyy
     * @date 2020/1/26 15:57
    */
    @Override
    public UserScheduleView postMySchedule(String startStationName, Date startTime, int userId, String endStationName) throws BusinessException {
        // 验证startStationName的合法性
        Station startStationResult = stationRespository.getByName(startStationName);
        if (startStationResult == null) {
            throw new BusinessException(EnumBusinessError.START_STATION_NOT_EXIST);
        }
        String startStationId = startStationResult.getId();
        // 验证endStationName的合法性
        Station endStationResult = stationRespository.getByName(endStationName);
        if (endStationResult == null) {
            throw new BusinessException(EnumBusinessError.END_STATION_NOT_EXIST);
        }
        String endStationId = endStationResult.getId();
        // UserScheduleView转UserSchedule
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setStartStationId(startStationId);
        userSchedule.setStartTime(startTime);
        userSchedule.setUserId(userId);
        userSchedule.setStatus(1);
        userSchedule.setEndStationId(endStationId);
        // 写入数据库
        UserSchedule userScheduleResult = userScheduleRepository.save(userSchedule);
        if (userScheduleResult == null) {
            throw new BusinessException(EnumBusinessError.FAILED_TO_CREATE_SCHEDULE);
        }
        int scheduleId = userScheduleResult.getId();
        // 从视图中读出数据
        UserScheduleView userScheduleViewResult = userScheduleViewRespository.getById(scheduleId);
        if (userScheduleViewResult == null) {
            throw new BusinessException(EnumBusinessError.FAILED_TO_CREATE_SCHEDULE);
        }
        return userScheduleViewResult;
    }



    /**
     * @author xyy
     * @date 2020/1/26 17:52
    */
    @Override
    public UserScheduleView stopMySchedule(int id, Date endTime) throws BusinessException {
        // 验证id的合法性
        UserSchedule userSchedule = userScheduleRepository.getById(id);
        if (userSchedule == null) {
            throw new BusinessException(EnumBusinessError.SCHEDULE_NOT_EXIST);
        }
        userSchedule.setEndTime(endTime);
        userSchedule.setStatus(2);
        // 写入数据库
        UserSchedule userScheduleResult = userScheduleRepository.save(userSchedule);
        if (userScheduleResult == null) {
            throw new BusinessException(EnumBusinessError.FAILED_TO_UPDATE_SCHEDULE);
        }
        int scheduleId = userScheduleResult.getId();
        // 从视图中读出数据
        UserScheduleView userScheduleViewResult = userScheduleViewRespository.getById(scheduleId);
        if (userScheduleViewResult == null) {
            throw new BusinessException(EnumBusinessError.FAILED_TO_UPDATE_SCHEDULE);
        }
        return userScheduleViewResult;
    }



    /**
     * @author xyy
     * @date 2020/1/26 18:51
    */
    @Override
    public UserScheduleView cancelMySchedule(int id) throws BusinessException {
        // 验证id
        UserSchedule userSchedule = userScheduleRepository.getById(id);
        if (userSchedule == null) {
            throw new BusinessException(EnumBusinessError.SCHEDULE_NOT_EXIST);
        }
        // 删除操作
        userScheduleRepository.delete(userSchedule);
        return null;
    }



    /**
     * @author xyy
     * @date 2020/1/26 19:07
    */
    @Override
    public List<UserScheduleView> listMySchedule(int id) throws BusinessException {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        // 验证id
        UserInfo userInfo = new UserInfo();
        try {
            userInfo = userServiceImpl.getUserInfoById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userInfo == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        }
        // 获取行程
        List<UserScheduleView> userScheduleViews = userScheduleViewRespository.getAllByUserId(id);
        return userScheduleViews;
    }
}
