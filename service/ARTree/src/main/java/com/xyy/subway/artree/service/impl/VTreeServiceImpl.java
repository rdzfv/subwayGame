package com.xyy.subway.artree.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xyy.subway.artree.dao.*;
import com.xyy.subway.artree.dto.UserTreeDTO;
import com.xyy.subway.artree.dto.VTreeTypeDetailDTO;
import com.xyy.subway.artree.entity.*;
import com.xyy.subway.artree.error.BusinessException;
import com.xyy.subway.artree.error.EnumBusinessError;
import com.xyy.subway.artree.service.DailyTaskService;
import com.xyy.subway.artree.service.VTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xyy
 * @date 2020/1/25 9:27
 * @description
 */
@Service
public class VTreeServiceImpl implements VTreeService {

    @Autowired
    private UserTreeOpRecordRepository userTreeOpRecordRepository;
    @Autowired
    private UserTreeRepository userTreeRepository;
    @Autowired
    private VTreeTypeRepository vTreeTypeRepository;
    @Autowired
    private VTreeUserRepository vTreeUserRepository;
    @Autowired
    private TipsRepository tipsRepository;
    @Autowired
    private Gift2Repository gift2Repository;
    @Autowired
    private HttpClient httpClient;
    @Autowired
    private DailyTaskService dailyTaskService;


    /**
     * @author xyy
     * @date 2020/2/18 14:36
    */
    @Override
    public JSONObject newAVtreeByUserId(int userId, String name) throws BusinessException {
        UserTree userTree = new UserTree();
        userTree.setUserId(userId);
        userTree.setName(name);
        userTree.setHeight(0.1F);
        userTree.setNextHeight(10);
        userTree.setLevel(1);

        ArrayList<VTreeTypeDetailDTO> vTreeTypeDetailDTOS = checkTreeTypeDetail(1);
        String url = vTreeTypeDetailDTOS.get(0).getUrl();
        userTree.setUrl(url);

        UserTree userTreeResult = userTreeRepository.save(userTree);

        // 开启一个ar种树的用户记录
        VTreeUser vTreeUser = new VTreeUser();
        vTreeUser.setUserId(userId);
        vTreeUser.setRemainWater(5);
        vTreeUser.setRemainUsedToMe(3);
        vTreeUser.setChange0(0);
        vTreeUserRepository.save(vTreeUser);

        // 要弹tip哦
        String tip = getATip();

        JSONObject object = new JSONObject();
        object.put("userTree", userTreeResult);
        object.put("isSurprise1", 0);
        object.put("isSurprise2", 0);
        object.put("isFirstLogin", 1);
        object.put("tip", tip);

        return object;
    }
    
    
    
    /**
     * @author xyy
     * @date 2020/2/18 14:36
    */
    @Override
    public ArrayList<VTreeTypeDetailDTO> checkTreeTypeDetail(int type) throws BusinessException {
        VTreeType vTreeType = vTreeTypeRepository.getById(type);
        String detail = vTreeType.getDetail();
        ArrayList<VTreeTypeDetailDTO> vTreeTypeDetailDTOS = new ArrayList<>();

        // 字符串转换为JSON数组
        JSONArray detailArray = JSONArray.parseArray(detail);

        for (int i = 0; i < detailArray.size(); i++) {
            JSONObject detailObject = (JSONObject)detailArray.get(i);

            String url = (String)detailObject.get("url");
            int level = (Integer)detailObject.get("level");
            int height = (Integer)detailObject.get("height");
            int nextHeight = (Integer)detailObject.get("nextHeight");
            int pro2 = (Integer)detailObject.get("probability2");
            float pro1 = Float.parseFloat(detailObject.get("probability1").toString());

            VTreeTypeDetailDTO vTreeTypeDetailDTO = new VTreeTypeDetailDTO();
            vTreeTypeDetailDTO.setHeight(height);
            vTreeTypeDetailDTO.setLevel(level);
            vTreeTypeDetailDTO.setNextHeight(nextHeight);
            vTreeTypeDetailDTO.setUrl(url);
            vTreeTypeDetailDTO.setProbability1(pro1);
            vTreeTypeDetailDTO.setProbability2(pro2);

            vTreeTypeDetailDTOS.add(vTreeTypeDetailDTO);
        }
        return vTreeTypeDetailDTOS;
    }




    /**
     * @author xyy
     * @date 2020/2/18 16:18
    */
    @Override
    public JSONObject getVTreeByUserId(int userId) throws BusinessException {
        // 读出树的信息
        UserTree userTreeResult = userTreeRepository.getByUserId(userId);
        if (userTreeResult == null) {
            throw new BusinessException(EnumBusinessError.NOT_HAVEA_TREE_YET);
        }
        // 读出用户信息
        VTreeUser vTreeUser = vTreeUserRepository.getByUserId(userId);

        int isFirstLogin = 0; // 告诉用户是否初次登录的标识
        String tip = "";

        // 获取当前时间并判断是否是新的一天
        SimpleDateFormat dfH = new SimpleDateFormat("HH"); //设置日期格式
        SimpleDateFormat dfD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前系统的小时数" + dfH.format(new Date())); // new Date()为获取当前系统时间
        System.out.println("当前系统的日期" + dfD.format(new Date()));
        String lastDate = vTreeUser.getUpdateTime().toString().split(" ")[0];
        System.out.println("上次登录的的日期" + lastDate);

        if (!lastDate.equals(dfD.format(new Date()))) {
            isFirstLogin = 1;
        }

        // 如果是初次登陆且处于第一阶段
        if (isFirstLogin == 1 && userTreeResult.getLevel() == 1) {
            vTreeUser.setRemainWater(vTreeUser.getRemainWater() + 5);
            vTreeUser.setRemainUsedToMe(3);
            vTreeUserRepository.save(vTreeUser);

            // 弹tip
            tip = getATip();

        }

        // 如果仅是初次登陆
        if (isFirstLogin == 1) {
            vTreeUser.setRemainWater(vTreeUser.getRemainWater() + 5);
            vTreeUser.setRemainUsedToMe(3);
        }


        // 每次登录就更新一次
        vTreeUser.setChange0(vTreeUser.getChange0() == 0 ? 1 : 0);
        VTreeUser vTreeUser2 = vTreeUserRepository.save(vTreeUser);

        JSONObject object = new JSONObject();
        object.put("userTree", userTreeResult);
        object.put("isSurprise1", 0);
        object.put("isSurprise2", 0);
        object.put("isFirstLogin", isFirstLogin);
        object.put("user", vTreeUser2);
        object.put("tip", tip);

        return object;
    }

    
    
    
    /**
     * @author xyy
     * @date 2020/2/18 17:10
    */
    @Override
    public JSONObject water(int userId, int userId2) throws BusinessException {
        // 取出被浇水的用户的树的信息
        UserTree userTree = userTreeRepository.getByUserId(userId2);

        // 判断当日是否有招标队伍类任务
        List<DailyTask> dailyTasks = dailyTaskService.getDailyTaskByUserId(userId);
        int size = dailyTasks.size();
        DailyTask dailyTask = dailyTasks.get(size - 1);
        int task2 = dailyTask.getTask2();
        int task3 = dailyTask.getTask3();
        int task4 = dailyTask.getTask4();
        int task5 = dailyTask.getTask5();
        int isAchieved = 0;
        if (userId == userId2) {
            if (task2 == 4) {
                dailyTask.setTodo2(1);
                isAchieved = 1;
            }
            if (task3 == 4) {
                dailyTask.setTodo3(1);
                isAchieved = 1;
            }
            if (task4 == 4) {
                dailyTask.setTodo4(1);
                isAchieved = 1;
            }
            if (task5 == 4) {
                dailyTask.setTodo5(1);
                isAchieved = 1;
            }
        } else {
            if (task2 == 5) {
                dailyTask.setTodo2(1);
                isAchieved = 1;
            }
            if (task3 == 5) {
                dailyTask.setTodo3(1);
                isAchieved = 1;
            }
            if (task4 == 5) {
                dailyTask.setTodo4(1);
                isAchieved = 1;
            }
            if (task5 == 5) {
                dailyTask.setTodo5(1);
                isAchieved = 1;
            }
        }
        dailyTaskService.updateDailyTask(dailyTask);
        // 如果完成了任务，添加奖励
        DailyTaskDetail dailyTaskDetail = dailyTaskService.getDailyTaskDetailById(6);
        String content = dailyTaskDetail.getContent();
        // 字符串转换为JSON数组
        JSONObject contentObject = JSONObject.parseObject(content);
        int awardMoney = (Integer)contentObject.get("awardMoney");
        int awardExp = (Integer)contentObject.get("awardExp");
        // 增加属性
        // 发送http请求到game2d服务
        String url = "http://47.101.146.28:7003/game2d/user/addExp?userId=" + userId + "&exp=" + awardExp;
        //get请求
        HttpMethod method = HttpMethod.GET;
        // 发送http请求并返回结果
        String Result = httpClient.client_GET(url,method);
        System.out.println(Result);

        // 发送http请求到game2d服务
        String url2 = "http://47.101.146.28:7003/game2d/user/addMoney?userId=" + userId + "&money=" + awardMoney;
        //get请求
        HttpMethod method2 = HttpMethod.GET;
        // 发送http请求并返回结果
        String Result2 = httpClient.client_GET(url2, method2);
        System.out.println(Result);


        // 取出浇水者的用户信息
        VTreeUser vTreeUser = vTreeUserRepository.getByUserId(userId);
        if (vTreeUser.getRemainWater() == 0) {
            throw new BusinessException(EnumBusinessError.NOT_ENOUGHT_WATER);
        }
        if (vTreeUser.getRemainUsedToMe() == 0 && userId == userId2) {
            throw new BusinessException(EnumBusinessError.EXCEED_WATER_TO_SELF);
        }
        vTreeUser.setRemainWater(vTreeUser.getRemainWater() - 1);
        if (userId == userId2) {
            vTreeUser.setRemainUsedToMe(vTreeUser.getRemainUsedToMe() - 1);
        }
        vTreeUserRepository.save(vTreeUser);

        userTree.setHeight((float)(userTree.getHeight() + 0.1));
        float height = userTree.getHeight();
        int isLevelUp = 0;
        int level = userTree.getLevel();
        int nextHeight = -1;
        // 判断有没有到下一阶段
        ArrayList<VTreeTypeDetailDTO> vTreeTypeDetailDTOS = checkTreeTypeDetail(1);
        int height1 = vTreeTypeDetailDTOS.get(level - 1).getHeight();
        if (level != 3) {
            nextHeight = vTreeTypeDetailDTOS.get(level).getHeight();
        }
        // 升级
        if (height >= nextHeight && nextHeight != -1) {
            level ++;
            isLevelUp = 1;
            userTree.setUrl(vTreeTypeDetailDTOS.get(level - 1).getUrl());
            if (level == 3) {
                nextHeight = -1;
            } else {
                nextHeight = vTreeTypeDetailDTOS.get(level).getHeight();
            }
        }
        userTree.setHeight(height);
        userTree.setLevel(level);
        userTree.setNextHeight(nextHeight);
        UserTree userTreeResult = userTreeRepository.save(userTree);

        // 记录浇水记录
        UserTreeOpRecord userTreeOpRecord = new UserTreeOpRecord();
        userTreeOpRecord.setUserId1(userId);
        userTreeOpRecord.setUserId2(userId2);
        userTreeOpRecordRepository.save(userTreeOpRecord);

        // 随机、是否触发奖励
        Gift2 gift2 = new Gift2();
        gift2 = null;
        if (level == 2 || level == 3) {
            float pro2 = vTreeTypeDetailDTOS.get(level - 1).getProbability2();
            int index = new Random().nextInt(100) + 1;
            System.out.println(index + "     " + pro2);
            if (index < pro2) {
                gift2 = getAGift2();
            }
        }

        JSONObject object = new JSONObject();
        object.put("userTree", userTreeResult);
        object.put("isLevelUp", isLevelUp);
        object.put("gift2", gift2);
        object.put("isAchieved", isAchieved);

        return object;
    }





    /**
     * @author xyy
     * @date 2020/2/19 15:45
    */
    @Override
    public String getATip() throws BusinessException {
        List<Tips> tips = tipsRepository.findAll();
        int size = tips.size();
        int index = new Random().nextInt(size) + 1;
        System.out.println(index);
        return tips.get(index - 1).getContent();
    }

    
    
    
    
    
    /**
     * @author xyy
     * @date 2020/2/19 19:49
    */
    @Override
    public Gift2 getAGift2() throws BusinessException {
        List<Gift2> gift2s = gift2Repository.findAll();
        int size = gift2s.size();
        int index = new Random().nextInt(size) + 1;
        System.out.println(index);
        return gift2s.get(index - 1);
    }

    
    
    
    
    /**
     * @author xyy
     * @date 2020/2/19 20:57
    */
    @Override
    public Boolean getGiftActually(int id, int userId) throws BusinessException {
        // 查出奖励详情
        Gift2 gift2 = gift2Repository.getById(id);
        int exp = gift2.getExp();
        int money = gift2.getMoney();
        int water = gift2.getWater();
        // 增加经验
        if (exp != 0) {
            // 发送http请求到game2d服务
            String url = "http://47.101.146.28:7003/game2d/user/addExp?userId=" + userId + "&exp=" + exp;
            //get请求
            HttpMethod method = HttpMethod.GET;
            // 发送http请求并返回结果
            String Result = httpClient.client_GET(url,method);
            System.out.println(Result);
        }
        // 增加金币
        if (money != 0) {
            // 发送http请求到game2d服务
            String url = "http://47.101.146.28:7003/game2d/user/addMoney?userId=" + userId + "&money=" + money;
            //get请求
            HttpMethod method = HttpMethod.GET;
            // 发送http请求并返回结果
            String Result = httpClient.client_GET(url,method);
            System.out.println(Result);
        }
        // 增加水滴
        if (water != 0) {
            VTreeUser vTreeUser = vTreeUserRepository.getByUserId(userId);
            vTreeUser.setRemainWater(vTreeUser.getRemainWater() + water);
            vTreeUser.setRemainUsedToMe(vTreeUser.getRemainWater() + water);
            vTreeUserRepository.save(vTreeUser);
        }
        return true;
    }

    
    
    
    
    
    /**
     * @author xyy
     * @date 2020/2/20 11:11
    */
    @Override
    public List<UserTreeOpRecord> getNearFiveActivities(int userId) throws BusinessException {
        List<UserTreeOpRecord> userTreeOpRecords = userTreeOpRecordRepository.getAllByUserId2(userId);
        ArrayList<UserTreeOpRecord> userTreeOpRecordArrayList = new ArrayList<>();
        int size = userTreeOpRecords.size();
        if (size > 5) {
            for (int i = size - 5; i < size; i ++){
                userTreeOpRecordArrayList.add(userTreeOpRecords.get(i));
            }
        } else {
            for (int i = 0; i < size; i++) {
                userTreeOpRecordArrayList.add(userTreeOpRecords.get(i));
            }
        }
        return userTreeOpRecordArrayList;
    }






    /**
     * @author xyy
     * @date 2020/2/20 14:47
    */
    @Override
    public UserTree getVFriendTreeByUserId(int userId) throws BusinessException {
        // 读出树的信息
        UserTree userTreeResult = userTreeRepository.getByUserId(userId);
        return userTreeResult;
    }





    /**
     * @author xyy
     * @date 2020/2/20 16:08
    */
    @Override
    public VTreeType getVTreeDetail() throws BusinessException {
        VTreeType vTreeType = vTreeTypeRepository.getById(1);
        return vTreeType;
    }






    /**
     * @author xyy
     * @date 2020/2/20 20:42
    */
    @Override
    public VTreeUser getUserInfo(int userId) throws BusinessException {
        VTreeUser vTreeUser = vTreeUserRepository.getByUserId(userId);
        return vTreeUser;
    }
}
