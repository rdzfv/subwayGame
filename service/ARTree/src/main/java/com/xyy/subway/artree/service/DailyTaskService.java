package com.xyy.subway.artree.service;

import com.xyy.subway.artree.entity.DailyTask;
import com.xyy.subway.artree.entity.DailyTaskDetail;
import com.xyy.subway.artree.error.BusinessException;

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
