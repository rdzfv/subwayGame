package com.xyy.subway.game2d.service.impl;

import com.xyy.subway.game2d.dao.DailyTaskDetailResposity;
import com.xyy.subway.game2d.dao.DailyTaskRespository;
import com.xyy.subway.game2d.entity.DailyTask;
import com.xyy.subway.game2d.entity.DailyTaskDetail;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.service.DailyTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/24 14:34
 * @description
 */
@Service
public class DailyTaskServiceImpl implements DailyTaskService {
    @Autowired
    private DailyTaskRespository dailyTaskRespository;
    @Autowired
    private DailyTaskDetailResposity dailyTaskDetailResposity;



    /**
     * @author xyy
     * @date 2020/2/24 14:38
    */
    @Override
    public List<DailyTask> getDailyTaskByUserId(int usereId) throws BusinessException {
        List<DailyTask> dailyTasks = dailyTaskRespository.getAllByUserId(usereId);
        return dailyTasks;
    }

    
    
    
    /**
     * @author xyy
     * @date 2020/2/24 14:58
    */
    @Override
    public DailyTask updateDailyTask(DailyTask dailyTask) throws BusinessException {

        DailyTask dailyTaskResult = dailyTaskRespository.save(dailyTask);
        return dailyTaskResult;
    }





    /**
     * @author xyy
     * @date 2020/2/24 18:01
    */
    @Override
    public DailyTaskDetail getDailyTaskDetailById(int id) throws BusinessException {
        DailyTaskDetail dailyTaskDetail = dailyTaskDetailResposity.getById(id);
        return dailyTaskDetail;
    }




    /**
     * @author xyy
     * @date 2020/2/24 19:50
    */
    @Override
    public DailyTask sign(int id) throws BusinessException {
        // 取出用户当天的任务
        List<DailyTask> dailyTasks = dailyTaskRespository.getAllByUserId(id);
        int size = dailyTasks.size();
        DailyTask dailyTask = dailyTasks.get(size - 1);

        // 把签到任务设为完成
        dailyTask.setTodo1(1);
        DailyTask dailyTaskResult = dailyTaskRespository.save(dailyTask);

        return dailyTaskResult;
    }
}
