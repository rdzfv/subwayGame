package com.xyy.subway.artree.controller;

import com.xyy.subway.artree.entity.*;
import com.xyy.subway.artree.entity.view.UserScheduleView;
import com.xyy.subway.artree.error.BusinessException;
import com.xyy.subway.artree.error.EnumBusinessError;
import com.xyy.subway.artree.response.CommonReturnType;
import com.xyy.subway.artree.service.ScheduleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author xyy
 * @date 2020/1/26 11:14
 * @description
 */
@Api(value="UserController",tags={"行程接口"})
@Controller("/schedule")
@RequestMapping("/schedule")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class ScheduleController extends BaseController {
    // 引入
    @Autowired
    private ScheduleService scheduleService;


    /**
     * @author xyy
     * @date 2020/1/26 11:24
    */
    @ApiOperation(value="通过行程id获取行程信息", tags={}, notes="测试")
    @RequestMapping(value = "/getScheduleById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="行程id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getScheduleById(@ApiParam(name="id", value = "行程id", required = true) int id) throws BusinessException {
        UserSchedule userScheduleInstanse = scheduleService.getScheduleById(id);
        if (userScheduleInstanse == null) throw new BusinessException(EnumBusinessError.SCHEDULE_NOT_EXIST);
        return CommonReturnType.create(userScheduleInstanse);
    }



    /**
     * @author xyy
     * @date 2020/1/26 11:56
    */
    @ApiOperation(value="通过城市id获取城市信息", tags={}, notes="测试")
    @RequestMapping(value = "/getCityById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="城市id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getCityById(@ApiParam(name="id", value = "城市id", required = true) int id) throws BusinessException {
        City cityInstanse = scheduleService.getCityById(id);
        if (cityInstanse == null) throw new BusinessException(EnumBusinessError.CITY_NOT_EXIST);
        return CommonReturnType.create(cityInstanse);
    }


    /**
     * @author xyy
     * @date 2020/1/26 11:56
     */
    @ApiOperation(value="通过路线id获取路线信息", tags={}, notes="测试")
    @RequestMapping(value = "/getRouteById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="路线id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getRouteById(@ApiParam(name="id", value = "路线id", required = true) int id) throws BusinessException {
        Route routeInstanse = scheduleService.getRouteById(id);
        if (routeInstanse == null) throw new BusinessException(EnumBusinessError.ROUTE_NOT_EXIST);
        return CommonReturnType.create(routeInstanse);
    }



    /**
     * @author xyy
     * @date 2020/1/26 12:21
    */
    @ApiOperation(value="通过地铁站id获取地铁站信息", tags={}, notes="测试")
    @RequestMapping(value = "getStationById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="地铁站id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getStationById(@ApiParam(name="id", value = "地铁站id", required = true) String id) throws BusinessException {
        Station stationInstanse = scheduleService.getStationById(id);
        if (stationInstanse == null) throw new BusinessException(EnumBusinessError.STATION_NOT_EXIST);
        return CommonReturnType.create(stationInstanse);
    }




   /**
    * @author xyy
    * @date 2020/1/26 13:22
   */
    @ApiOperation(value="通过车次id获取车次信息", tags={}, notes="测试")
    @RequestMapping(value = "getTrainScheduleById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="车次id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getTrainScheduleById(@ApiParam(name="id", value = "车次id", required = true) int id) throws BusinessException {
        TrainSchedule trainSchedule = scheduleService.getTrainScheduleById(id);
        if (trainSchedule == null) throw new BusinessException(EnumBusinessError.TRAIN_SCHEDULE_NOT_EXIST);
        return CommonReturnType.create(trainSchedule);
    }



   /**
    * @author xyy
    * @date 2020/1/26 15:44
   */
    @ApiOperation(value="通过行程id获取行程信息", tags={}, notes="测试")
    @RequestMapping(value = "getUserScheduleById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="行程id", dataType="int", paramType = "query", example="0")
    })
    @ResponseBody
    public CommonReturnType getUserScheduleById(@ApiParam(name="id", value = "行程id", required = true) int id) throws BusinessException {
        UserScheduleView userScheduleView = scheduleService.getUserScheduleById(id);
        if (userScheduleView == null) throw new BusinessException(EnumBusinessError.SCHEDULE_NOT_EXIST);
        return CommonReturnType.create(userScheduleView);
    }



    /**
     * @author xyy
     * @date 2020/1/26 15:44
    */
    @ApiOperation(value="提交行程", tags={}, notes="用户进入闸机前端获取当时时间，通过定位/用户选择给出地铁站名称（名称需要和数据库的一致），用户id，前端还需让用户选择目的地铁站名称，然后提交行程，提交行程后行程状态置为\"进行中\"（1）")
    @RequestMapping(value = "postMySchedule", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="startStationName", value="出发站点名称", dataType="string", paramType = "query", example=""),
            @ApiImplicitParam(name="startTime", value="出发时间", dataType="datetime", paramType = "query", example="2020/01/26 17:04:00"),
            @ApiImplicitParam(name="userId", value="用户Id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="endStationName", value="目的站点名称", dataType="string", paramType = "query", example=""),
    })
    @ResponseBody
    public CommonReturnType postMySchedule(@ApiParam(name="startStationName", value = "出发站点名称", required = true) String startStationName,
                                           @ApiParam(name="startTime", value = "出发时间", required = true) Date startTime,
                                           @ApiParam(name="userId", value = "用户Id", required = true) int userId,
                                           @ApiParam(name="endStationName", value = "目的站点名称", required = true) String endStationName
    ) throws BusinessException {
        UserScheduleView userScheduleViewInstance = scheduleService.postMySchedule(startStationName, startTime, userId, endStationName);
        if (userScheduleViewInstance == null) throw new BusinessException(EnumBusinessError.FAILED_TO_CREATE_SCHEDULE);
        return CommonReturnType.create(userScheduleViewInstance);
    }




    /**
     * @author xyy
     * @date 2020/1/26 15:44
     */
    @ApiOperation(value="结束行程", tags={}, notes="用户离开闸机前端获取当时时间，订单id，提交行程后行程状态置为\"未付款\"（2）")
    @RequestMapping(value = "stopMySchedule", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="订单Id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="endTime", value="到达时间", dataType="datetime", paramType = "query", example="2020/01/26 17:04:00")
    })
    @ResponseBody
    public CommonReturnType stopMySchedule(@ApiParam(name="id", value = "订单Id", required = true) int id,
                                           @ApiParam(name="endTime", value = "到达时间", required = true) Date endTime
    ) throws BusinessException {
        UserScheduleView userScheduleViewInstance = scheduleService.stopMySchedule(id, endTime);
        if (userScheduleViewInstance == null) throw new BusinessException(EnumBusinessError.FAILED_TO_UPDATE_SCHEDULE);
        return CommonReturnType.create(userScheduleViewInstance);
    }




    /**
     * @author xyy
     * @date 2020/1/26 15:44
     */
    @ApiOperation(value="取消行程", tags={}, notes="")
    @RequestMapping(value = "cancelMySchedule", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="订单Id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType cancelMySchedule(@ApiParam(name="id", value = "订单Id", required = true) int id) throws BusinessException {
        UserScheduleView userScheduleViewInstance = scheduleService.cancelMySchedule(id);
        return CommonReturnType.create("success");
    }




    /**
     * @author xyy
     * @date 2020/1/26 19:04
    */
    @ApiOperation(value="获取我的全部行程", tags={}, notes="")
    @RequestMapping(value = "listMySchedule", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="用户Id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType listMySchedule(@ApiParam(name="id", value = "用户Id", required = true) int id) throws BusinessException {
        List<UserScheduleView> userScheduleViewInstances = scheduleService.listMySchedule(id);
        return CommonReturnType.create(userScheduleViewInstances);
    }
}
