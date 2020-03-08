package com.xyy.subway.game2d.controller;

import com.xyy.subway.game2d.entity.DailyTask;
import com.xyy.subway.game2d.entity.DailyTaskDetail;
import com.xyy.subway.game2d.entity.VRoute;
import com.xyy.subway.game2d.error.BusinessException;
import com.xyy.subway.game2d.error.EnumBusinessError;
import com.xyy.subway.game2d.response.CommonReturnType;
import com.xyy.subway.game2d.service.DailyTaskService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/24 13:13
 * @description
 */
@Api(value="VDailyTask",tags={"每日任务接口"})
@Controller("/dailyTask")
@RequestMapping("/dailyTask")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class DailyTaskController extends BaseController {
    // 引入
    @Autowired
    private DailyTaskService dailyTaskService;


    /**
     * @author xyy
     * @date 2020/2/24 14:16
    */
    @ApiOperation(value="获取每日任务", tags={}, notes="")
    @RequestMapping(value = "/getDailyTaskByUserId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getDailyTaskByUserId(@ApiParam(name="userId", value = "用户id", required = true) int userId) throws BusinessException {
        List<DailyTask> dailyTasks = dailyTaskService.getDailyTaskByUserId(userId);
        int size = dailyTasks.size();
        DailyTask dailyTask = dailyTasks.get(size - 1);
        return CommonReturnType.create(dailyTask);
    }



//    /**
//     * @author xyy
//     * @date 2020/2/24 14:16
//     */
//    @ApiOperation(value="更新任务完成情况", tags={}, notes="")
//    @RequestMapping(value = "/updateDailyTask", method = RequestMethod.GET)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example=""),
//            @ApiImplicitParam(name="taskNo", value="第几项任务", dataType="int", paramType = "query", example="")
//    })
//    @ResponseBody
//    public CommonReturnType updateDailyTask(@ApiParam(name="userId", value = "用户id", required = true) int userId,
//                                            @ApiParam(name="taskNo", value = "第几项任务", required = true) int taskNo
//    ) throws BusinessException {
//        DailyTask dailyTask = dailyTaskService.updateDailyTask(userId, taskNo);
//        return CommonReturnType.create(dailyTask);
//    }




    /**
     * @author xyy
     * @date 2020/2/24 17:54
    */
    @ApiOperation(value="根据每日任务id获取任务细节", tags={}, notes="")
    @RequestMapping(value = "/getDailyTaskDetailById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="每日任务id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType getDailyTaskDetailById(@ApiParam(name="id", value = "每日任务id", required = true) int id) throws BusinessException {
        DailyTaskDetail dailyTaskDetail = dailyTaskService.getDailyTaskDetailById(id);
        return CommonReturnType.create(dailyTaskDetail);
    }




    /**
     * @author xyy
     * @date 2020/2/24 17:54
     */
    @ApiOperation(value="签到接口", tags={}, notes="这个接口是假装签到，实际连续登录天数后台在每一次的获取用户信息都在计算")
    @RequestMapping(value = "/sign", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType sign(@ApiParam(name="id", value = "用户id", required = true) int id) throws BusinessException {
        DailyTask dailyTaskDetail = dailyTaskService.sign(id);
        return CommonReturnType.create(dailyTaskDetail);
    }





    /**
     * @author xyy
     * @date 2020/3/8 11:14
    */
    @ApiOperation(value="获取全部每日任务", tags={}, notes="")
    @RequestMapping(value = "/getAllDailyTasks", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getAllDailyTasks() throws BusinessException {
        List<DailyTaskDetail> dailyTaskDetail = dailyTaskService.getAllDailyTasks();
        return CommonReturnType.create(dailyTaskDetail);
    }




    /**
     * @author xyy
     * @date 2020/3/8 11:14
     */
    @ApiOperation(value="修改签到设定", tags={}, notes="")
    @RequestMapping(value = "/updateSignTask", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="expADay", value="每天可以获得的经验", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="moneyADay", value="每天可以获得的金币", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="maxExp", value="最多可以获得的经验", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="maxMoney", value="最多可以获得的金币", dataType="int", paramType = "query", example=""),
    })
    @ResponseBody
    public CommonReturnType updateSignTask(@ApiParam(name="expADay", value = "每天可以获得的经验", required = true) int expADay,
                                           @ApiParam(name="moneyADay", value = "每天可以获得的金币", required = true) int moneyADay,
                                           @ApiParam(name="maxExp", value = "最多可以获得的经验", required = true) int maxExp,
                                           @ApiParam(name="maxMoney", value = "最多可以获得的金币", required = true) int maxMoney
    ) throws BusinessException {
        List<DailyTaskDetail> dailyTaskDetail = dailyTaskService.updateSignTask(expADay, moneyADay, maxExp, maxMoney);
        return CommonReturnType.create(dailyTaskDetail);
    }





    /**
     * @author xyy
     * @date 2020/3/8 13:06
    */
    @ApiOperation(value="修改其余任务设定", tags={}, notes="")
    @RequestMapping(value = "/updateOtherTask", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="设定id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="awardMoney", value="奖励金币", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="awardExp", value="奖励经验", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType updateOtherTask(@ApiParam(name="id", value = "奖励金币", required = true) int id,
                                            @ApiParam(name="awardMoney", value = "奖励金币", required = true) int awardMoney,
                                            @ApiParam(name="awardExp", value = "奖励经验", required = true) int awardExp
    ) throws BusinessException {
        List<DailyTaskDetail> dailyTaskDetail = dailyTaskService.updateOtherTask(id, awardMoney, awardExp);
        return CommonReturnType.create(dailyTaskDetail);
    }
}
