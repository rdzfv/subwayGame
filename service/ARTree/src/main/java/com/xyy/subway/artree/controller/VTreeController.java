package com.xyy.subway.artree.controller;

import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.artree.dto.UserTreeDTO;
import com.xyy.subway.artree.entity.*;
import com.xyy.subway.artree.error.BusinessException;
import com.xyy.subway.artree.response.CommonReturnType;
import com.xyy.subway.artree.service.VTreeService;
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
 * @date 2020/1/25 9:01
 * @description
 */
@Api(value="VTreeController",tags={"虚拟树接口"})
@Controller("/vtree")
@RequestMapping("/vtree")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class VTreeController extends BaseController {
    // 引入
    @Autowired
    private VTreeService vTreeService;


    /**
     * @author xyy
     * @date 2020/1/22 20:27
     */
    @ApiOperation(value="领养一棵树", tags={}, notes="")
    @RequestMapping(value = "/newVTreeByUserId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="name", value="树的名字", dataType="String", paramType = "query", example=""),
    })
    @ResponseBody
    public CommonReturnType newVTreeByUserId(@ApiParam(name="userId", value = "用户id", required = true) int userId,
                                             @ApiParam(name="name", value = "树的名字", required = true) String name
    ) throws BusinessException {
        JSONObject userTree = vTreeService.newAVtreeByUserId(userId, name);
        return CommonReturnType.create(userTree);
    }



    /**
     * @author xyy
     * @date 2020/2/18 16:16
     */
    @ApiOperation(value="获取自己的树", tags={}, notes="")
    @RequestMapping(value = "/getVTreeByUserId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType getVTreeByUserId(@ApiParam(name="userId", value = "用户id", required = true) int userId) throws BusinessException {
        JSONObject userTree = vTreeService.getVTreeByUserId(userId);
        return CommonReturnType.create(userTree);
    }




    /**
     * @author xyy
     * @date 2020/2/18 16:50
    */
    @ApiOperation(value="浇水", tags={}, notes="")
    @RequestMapping(value = "/water", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="userId2", value="浇水对象id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType water(@ApiParam(name="userId", value = "用户id", required = true) int userId,
                                  @ApiParam(name="userId2", value = "浇水对象id", required = true) int userId2
    ) throws BusinessException {
        JSONObject userTree = vTreeService.water(userId, userId2);
        return CommonReturnType.create(userTree);
    }





    /**
     * @author xyy
     * @date 2020/2/18 16:50
     */
    @ApiOperation(value="随机获取一条tip", tags={}, notes="")
    @RequestMapping(value = "/getTip", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getTip() throws BusinessException {
        String tip = vTreeService.getATip();
        return CommonReturnType.create(tip);
    }






    /**
     * @author xyy
     * @date 2020/2/19 19:55
    */
    @ApiOperation(value="随机获取一条奖励详情", tags={}, notes="")
    @RequestMapping(value = "/getGift", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getGift() throws BusinessException {
        Gift2 gift2 = vTreeService.getAGift2();
        return CommonReturnType.create(gift2);
    }





    /**
     * @author xyy
     * @date 2020/2/19 19:55
     */
    @ApiOperation(value="获取降临的奖励", tags={}, notes="")
    @RequestMapping(value = "/getGiftActually", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="奖励id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType getGiftActually(@ApiParam(name="id", value = "奖励id", required = true) int id,
                                            @ApiParam(name="userId", value = "用户id", required = true) int userId
    ) throws BusinessException {
        Boolean success = vTreeService.getGiftActually(id, userId);
        return CommonReturnType.create(success);
    }




    /**
     * @author xyy
     * @date 2020/2/19 19:55
     */
    @ApiOperation(value="获取近五次的互动", tags={}, notes="")
    @RequestMapping(value = "/getNearFiveActivities", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType getNearFiveActivities(int userId) throws BusinessException {
        List<UserTreeOpRecord> userTreeOpRecords = vTreeService.getNearFiveActivities(userId);
        return CommonReturnType.create(userTreeOpRecords);
    }




    /**
     * @author xyy
     * @date 2020/2/18 16:16
     */
    @ApiOperation(value="获取好友的树", tags={}, notes="")
    @RequestMapping(value = "/getVFriendTreeByUserId", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType getVFriendTreeByUserId(@ApiParam(name="userId", value = "用户id", required = true) int userId) throws BusinessException {
        UserTree userTree = vTreeService.getVFriendTreeByUserId(userId);
        return CommonReturnType.create(userTree);
    }




   /**
    * @author xyy
    * @date 2020/2/20 15:59
   */
    @ApiOperation(value="获取树的形态细节", tags={}, notes="")
    @RequestMapping(value = "/getVTreeDetail", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getVTreeDetail() throws BusinessException {
        VTreeType vTreeType = vTreeService.getVTreeDetail();
        return CommonReturnType.create(vTreeType);
    }




    /**
     * @author xyy
     * @date 2020/2/20 15:59
     */
    @ApiOperation(value="获取用户信息（AR种树相关）",tags={}, notes="")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataType="int", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType getUserInfo(@ApiParam(name="userId", value = "用户id", required = true) int userId) throws BusinessException {
        VTreeUser vTreeType = vTreeService.getUserInfo(userId);
        return CommonReturnType.create(vTreeType);
    }




    /**
     * @author xyy
     * @date 2020/3/7 15:54
    */
    @ApiOperation(value="获取全部浇水普通奖励",tags={}, notes="")
    @RequestMapping(value = "/getAllWaterGiftt2", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getAllWaterGiftt2() throws BusinessException {
        List<Gift2> gift2s = vTreeService.getAllWaterGiftt2();
        return CommonReturnType.create(gift2s);
    }




    /**
     * @author xyy
     * @date 2020/3/7 16:01
    */
    @ApiOperation(value="修改浇水普通奖励",tags={}, notes="")
    @RequestMapping(value = "/updateWaterGiftt2", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="id", value="礼物id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="exp", value="奖励经验", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="money", value="奖励金币", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="water", value="奖励水滴", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="content", value="文字内容", dataType="string", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType updateWaterGiftt2(@ApiParam(name="id", value = "礼物id", required = true) int id,
                                              @ApiParam(name="exp", value = "奖励经验", required = true) int exp,
                                              @ApiParam(name="money", value = "奖励金币", required = true) int money,
                                              @ApiParam(name="water", value = "奖励水滴", required = true) int water,
                                              @ApiParam(name="content", value = "文字内容", required = true) String content
    ) throws BusinessException {
        Gift2 gift2 = new Gift2();
        gift2.setWater(water);
        gift2.setContent(content);
        gift2.setExp(exp);
        gift2.setMoney(money);
        gift2.setId(id);

        List<Gift2> gift2Result = vTreeService.updateWaterGiftt2(gift2);
        return CommonReturnType.create(gift2Result);
    }




    /**
     * @author xyy
     * @date 2020/3/7 18:25
    */
    @ApiOperation(value="新增浇水普通奖励",tags={}, notes="")
    @RequestMapping(value = "/addWaterGiftt2", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="exp", value="奖励经验", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="money", value="奖励金币", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="water", value="奖励水滴", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="content", value="文字内容", dataType="string", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType addWaterGiftt2(@ApiParam(name="exp", value = "奖励经验", required = true) int exp,
                                              @ApiParam(name="money", value = "奖励金币", required = true) int money,
                                              @ApiParam(name="water", value = "奖励水滴", required = true) int water,
                                              @ApiParam(name="content", value = "文字内容", required = true) String content
    ) throws BusinessException {
        Gift2 gift2 = new Gift2();
        gift2.setWater(water);
        gift2.setContent(content);
        gift2.setExp(exp);
        gift2.setMoney(money);

        List<Gift2> gift2Result = vTreeService.addWaterGiftt2(gift2);
        return CommonReturnType.create(gift2Result);
    }





    /**
     * @author xyy
     * @date 2020/3/9 10:32
    */
    @ApiOperation(value="获取全部tip",tags={}, notes="")
    @RequestMapping(value = "/getAllTips", method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getAllTips() throws BusinessException {
        List<Tips> gift2s = vTreeService.getAllTips();
        return CommonReturnType.create(gift2s);
    }






    /**
     * @author xyy
     * @date 2020/3/9 10:47
    */
    @ApiOperation(value="根据id更新tip",tags={}, notes="")
    @RequestMapping(value = "/updateTipsById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="tipId", value="秘籍Id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="content", value="秘籍内容", dataType="string", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType updateTipsById(@ApiParam(name="tipId", value = "秘籍Id", required = true) int tipId,
                                           @ApiParam(name="content", value = "秘籍内容", required = true) String content
    ) throws BusinessException {
        List<Tips> gift2s = vTreeService.updateTipsById(tipId, content);
        return CommonReturnType.create(gift2s);
    }






    /**
     * @author xyy
     * @date 2020/3/9 11:05
    */
    @ApiOperation(value="新增tip",tags={}, notes="")
    @RequestMapping(value = "/addTip", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="content", value="秘籍内容", dataType="string", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType addTipsById(@ApiParam(name="content", value = "秘籍内容", required = true) String content) throws BusinessException {
        List<Tips> gift2s = vTreeService.addTipsById(content);
        return CommonReturnType.create(gift2s);
    }





    /**
     * @author xyy
     * @date 2020/3/9 11:17
    */
    @ApiOperation(value="根据id删除tip",tags={}, notes="")
    @RequestMapping(value = "/deleteTipsById", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(name="tipId", value="秘籍Id", dataType="int", paramType = "query", example=""),
            @ApiImplicitParam(name="content", value="秘籍内容", dataType="string", paramType = "query", example="")
    })
    @ResponseBody
    public CommonReturnType deleteTipsById(@ApiParam(name="tipId", value = "秘籍Id", required = true) int tipId,
                                           @ApiParam(name="content", value = "秘籍内容", required = true) String content
    ) throws BusinessException {
        List<Tips> gift2s = vTreeService.deleteTipsById(tipId, content);
        return CommonReturnType.create(gift2s);
    }
}
