package com.xyy.subway.game2d.service.impl;

import com.alibaba.fastjson.JSONObject;
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

    
    
    
    
    
    /**
     * @author xyy
     * @date 2020/3/8 11:22
    */
    @Override
    public List<DailyTaskDetail> getAllDailyTasks() throws BusinessException {
        List<DailyTaskDetail> dailyTaskDetails = dailyTaskDetailResposity.findAll();
        return dailyTaskDetails;
    }





    /**
     * @author xyy
     * @date 2020/3/8 11:50
    */
    @Override
    public List<DailyTaskDetail> updateSignTask(int expADay, int moneyADay, int maxExp, int maxMoney) throws BusinessException {
        JSONObject object = new JSONObject();
        object.put("expADay", expADay);
        object.put("moneyADay", moneyADay);
        object.put("maxExp", maxExp);
        object.put("maxMoney", maxMoney);

        String content = object.toString();

        // 取出原来的
        DailyTaskDetail dailyTaskDetailResult = dailyTaskDetailResposity.getById(1);
        String words = dailyTaskDetailResult.getWords();

        DailyTaskDetail dailyTaskDetail = new DailyTaskDetail();
        dailyTaskDetail.setId(1);
        dailyTaskDetail.setContent(content);
        dailyTaskDetail.setWords(words);
        dailyTaskDetailResposity.save(dailyTaskDetail);

        // 再次查出全部的任务
        List<DailyTaskDetail> dailyTaskDetails = dailyTaskDetailResposity.findAll();

        return dailyTaskDetails;
    }

    
    
    
    
    
    /**
     * @author xyy
     * @date 2020/3/8 13:40
    */
    @Override
    public List<DailyTaskDetail> updateOtherTask(int id, int awardMoney, int awardExp) throws BusinessException {
        // 取出原设定
        DailyTaskDetail dailyTaskDetailResult = dailyTaskDetailResposity.getById(id);
        String words = dailyTaskDetailResult.getWords();

        JSONObject object = new JSONObject();
        object.put("awardMoney", awardMoney);
        object.put("awardExp", awardExp);
        String content = object.toString();

        DailyTaskDetail dailyTaskDetail = new DailyTaskDetail();
        dailyTaskDetail.setId(id);
        dailyTaskDetail.setWords(words);
        dailyTaskDetail.setContent(content);
        dailyTaskDetailResposity.save(dailyTaskDetail);

        // 查出全部的
        List<DailyTaskDetail> dailyTaskDetails = dailyTaskDetailResposity.findAll();

        return dailyTaskDetails;
    }
}
