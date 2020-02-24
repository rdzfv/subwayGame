package com.xyy.subway.game2d.service;

import com.xyy.subway.game2d.entity.DailyTask;
import com.xyy.subway.game2d.entity.DailyTaskDetail;
import com.xyy.subway.game2d.error.BusinessException;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/24 14:32
 * @description
 */
public interface DailyTaskService {
    List<DailyTask> getDailyTaskByUserId(int usereId) throws BusinessException;
    DailyTask updateDailyTask(DailyTask dailyTask) throws BusinessException;
    DailyTaskDetail getDailyTaskDetailById(int id) throws BusinessException;
    DailyTask sign(int id) throws BusinessException;
}
