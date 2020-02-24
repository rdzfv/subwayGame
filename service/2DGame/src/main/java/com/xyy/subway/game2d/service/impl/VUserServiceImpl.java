package com.xyy.subway.game2d.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.game2d.dao.*;
import com.xyy.subway.game2d.dto.ExpAndLevelDTO;
import com.xyy.subway.game2d.entity.*;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.service.LevelCanAndCantService;
import com.xyy.subway.game2d.service.ToolService;
import com.xyy.subway.game2d.service.VUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author xyy
 * @date 2020/1/25 9:27
 * @description
 */
@Service
@Scope("prototype")
public class VUserServiceImpl implements VUserService {

    @Autowired
    private VuserRepository vUserRepository;
    @Autowired
    private DailyTaskDetailResposity dailyTaskDetailResposity;
    @Autowired
    private DailyTaskRespository dailyTaskRespository;
    
   /**
    * @author xyy
    * @date 2020/1/25 11:17
   */
    @Override
    public VUser getVUserInfoById(int id) throws BusinessException {
        VUser user = vUserRepository.getByUserId(id);
        if (user == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        // 判断是否是初次登陆
        int isFirstLogin = 0; // 告诉用户是否初次登录的标识
        String tip = "";

        // 获取当前时间并判断是否是新的一天
        SimpleDateFormat dfH = new SimpleDateFormat("HH"); //设置日期格式
        SimpleDateFormat dfD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前系统的小时数" + dfH.format(new Date())); // new Date()为获取当前系统时间
        System.out.println("当前系统的日期" + dfD.format(new Date()));
        String lastDate = user.getUpdateTime().toString().split(" ")[0];
        System.out.println("上次登录的的日期" + lastDate);

        if (!lastDate.equals(dfD.format(new Date()))) {
            isFirstLogin = 1;
        }

        // 如果是初次登陆
        if (isFirstLogin == 1) {
            // 连续登录天数加一
            user.setLoginDays(user.getLoginDays() + 1);
            // 初始化今天的每日任务
            List<DailyTaskDetail> dailyTaskDetails = dailyTaskDetailResposity.findAll();
            // 在除了第一条的情况下随机选四条
            int size = dailyTaskDetails.size();
            ArrayList<Integer> dailyTasks = new ArrayList<>();
            dailyTasks.add(1);
            for (int i = 0; i < 4; i++) {
                while (true) {
                    int flag0 = 0;
                    int index = new Random().nextInt(size) + 1;
                    System.out.println(index);
                    for (int j = 0; j < dailyTasks.size(); j++) {
                        if (dailyTasks.get(j) == index) {
                            flag0 = 1;
                        }
                    }
                    // 如果没有重复就不用重复生产
                    if (flag0 == 0) {
                        dailyTasks.add(index);
                        break;
                    }
                }
            }
            // 生成的任务写进每日任务表
            DailyTask dailyTask = new DailyTask();
            dailyTask.setUserId(id);
            dailyTask.setTask1(1);
            dailyTask.setTodo1(0);
            dailyTask.setTask2(dailyTasks.get(1));
            dailyTask.setTodo2(0);
            dailyTask.setTask3(dailyTasks.get(2));
            dailyTask.setTodo3(0);
            dailyTask.setTask4(dailyTasks.get(3));
            dailyTask.setTodo4(0);
            dailyTask.setTask5(dailyTasks.get(4));
            dailyTask.setTodo5(0);
            dailyTaskRespository.save(dailyTask);
        }

        // 每次登录就更新一次
        user.setChange0(user.getChange0() == 0 ? 1 : 0);
        VUser vUser = vUserRepository.save(user);

        return vUser;
    }



    /**
     * @author xyy
     * @date 2020/1/27 12:19
    */
    @Override
    public VUser updateUserInfo(VUser vUser) throws BusinessException {
        // 数据库查出待更新对象
        VUser vUserResult = vUserRepository.getByUserId(vUser.getUserId());
        if (vUserResult == null) throw new BusinessException(EnumBusinessError.USER_NOT_EXIST);
        // 使用hutool BeanUtil进行对象拷贝（忽略null值）
        BeanUtil.copyProperties(vUser, vUserResult, true, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
        VUser vUserInstance = vUserRepository.save(vUserResult);
        return vUserInstance;
    }

    
    
    
    /**
     * @author xyy
     * @date 2020/2/10 13:51
    */
    @Override
    public VUser newAVUserByUserId(int userId) throws BusinessException {
        VUser vUser = new VUser();
        vUser.setUserId(userId);
        System.out.println(vUser.getUserId());
        vUser.setExp(0L);
        vUser.setSatisfactionDegree(100);
        vUser.setSecurity(100);
        vUser.setUncrowedness(100);
        vUser.setCleaness(100);
        vUser.setMoney(2000L);
        vUser.setAvailableWorkers(0);
        vUser.setLevel(1);
        vUser.setVisitorFlowrate(0L);
        vUser.setWorkers(0);
        vUser.setLoginDays(1);
        vUser.setChange0(0);

        VUser vUserResult = vUserRepository.save(vUser);

        return vUserResult;
    }
}
