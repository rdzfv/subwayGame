package com.xyy.subway.trueword.service;

import com.xyy.subway.trueword.entity.*;
import com.xyy.subway.trueword.entity.view.UserScheduleView;
import com.xyy.subway.trueword.error.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/26 11:24
 * @description
 */
@Service
public interface ScheduleService {
    UserSchedule getScheduleById(int id) throws BusinessException;
    City getCityById(int id) throws BusinessException;
    Route getRouteById(int id) throws BusinessException;
    Station getStationById(String id) throws BusinessException;
    TrainSchedule getTrainScheduleById(int id) throws BusinessException;
    UserScheduleView getUserScheduleById(int id) throws BusinessException;
    UserScheduleView postMySchedule(String startStationId, Date startTime, int userId, String endStationName) throws BusinessException;
    UserScheduleView stopMySchedule(int id, Date endTime) throws BusinessException;
    UserScheduleView cancelMySchedule(int id) throws BusinessException;
    List<UserScheduleView> listMySchedule(int id) throws BusinessException;
}
