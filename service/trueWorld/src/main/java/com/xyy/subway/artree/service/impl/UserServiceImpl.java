package com.xyy.subway.artree.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.xyy.subway.artree.constant.UserInfoConstant;
import com.xyy.subway.artree.dao.UserRepository;
import com.xyy.subway.artree.dto.FriendsDTO;
import com.xyy.subway.artree.dto.ShowUserInfoDTO;
import com.xyy.subway.artree.dto.UpdateUserInfoDTO;
import com.xyy.subway.artree.entity.UserInfo;
import com.xyy.subway.artree.error.BusinessException;
import com.xyy.subway.artree.error.EnumBusinessError;
import com.xyy.subway.artree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author xyy
 * @date 2020/1/25 9:27
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    /**
     * @author xyy
     * @date 2020/1/25 9:29
    */
    @Override
    public UserInfo getUserInfoById(int id) throws BusinessException {
        UserInfo user = userRepository.getByUserId(id);
        return user;
    }


    /**
     * @author xyy
     * @date 2020/1/25 13:50
    */
    @Override
    public ShowUserInfoDTO login(String openId) throws BusinessException {
        // 检查该用户是否已经存在
        UserInfo user = userRepository.getByOpenId(openId);
        if (user == null) {
            return null;
        }

        ShowUserInfoDTO showUserInfoDTO = new ShowUserInfoDTO();
        showUserInfoDTO.setUserId(user.getUserId());
        showUserInfoDTO.setName(user.getName());
        showUserInfoDTO.setIcon_url(user.getIcon_url());
        showUserInfoDTO.setFriend_ids(user.getFriend_ids());
        showUserInfoDTO.setCreateTime(user.getCreateTime());
        showUserInfoDTO.setModifyTime(user.getModifyTime());

        return showUserInfoDTO;
    }



    /**
     * @author xyy
     * @date 2020/1/25 14:29
    */
    @Override
    public ShowUserInfoDTO updateUserInfo(UpdateUserInfoDTO updateUserInfoDTO) {
        // 验证id
        int id = updateUserInfoDTO.getUserId();
        UserInfo userInfo = userRepository.getByUserId(id);
        // 更新数据
        if (updateUserInfoDTO.getName() != null) {
            userInfo.setName(updateUserInfoDTO.getName());
        }
        if (updateUserInfoDTO.getIcon_url() != null) {
            updateUserInfoDTO.setIcon_url(updateUserInfoDTO.getIcon_url());
        }
        UserInfo userInfoResult = userRepository.save(userInfo);
        // entity转model
        ShowUserInfoDTO showUserInfoDTOResult = new ShowUserInfoDTO();
        showUserInfoDTOResult.setUserId(userInfoResult.getUserId());
        showUserInfoDTOResult.setName(userInfoResult.getName());
        showUserInfoDTOResult.setIcon_url(userInfoResult.getIcon_url());
        showUserInfoDTOResult.setFriend_ids(userInfoResult.getFriend_ids());
        showUserInfoDTOResult.setCreateTime(userInfoResult.getCreateTime());
        showUserInfoDTOResult.setModifyTime(userInfoResult.getModifyTime());
        return showUserInfoDTOResult;
    }



    /**
     * @author xyy
     * @date 2020/1/25 16:20
    */
    @Override
    public ArrayList<FriendsDTO> listMyFriends(int id) {
        ArrayList<FriendsDTO> friendsDTOS = new ArrayList<FriendsDTO>();
        // 通过用户id获取他的friendIds
        UserInfo user = userRepository.getByUserId(id);
        String friendIds = user.getFriend_ids();
        // 获取每一个好友的信息
        JSONArray friendIdsArray = JSONArray.parseArray(friendIds);
        if (friendIdsArray == null) {
            friendIds = "[]";
            friendIdsArray = JSONArray.parseArray(friendIds);
        }
        for (int i = 0; i < friendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendIdsArray.get(i);
            System.out.println(afriend.get("friendId"));
            int aFriendId = (Integer)afriend.get("friendId");
            String status = (String)afriend.get("status");
            UserInfo aFriend = userRepository.getByUserId(aFriendId);
            // entity转model
            FriendsDTO friendsDTO = new FriendsDTO();
            friendsDTO.setUserId(aFriend.getUserId());
            friendsDTO.setName(aFriend.getName());
            friendsDTO.setIcon_url(aFriend.getIcon_url());
            friendsDTO.setCreateTime(aFriend.getCreateTime());
            friendsDTO.setStatus(status);

            friendsDTOS.add(friendsDTO);
        }
        return friendsDTOS;
    }



    /**
     * @author xyy
     * @date 2020/1/25 17:11
    */
    @Override
    public UserInfo applyForFriend(int id, String friendName) throws BusinessException {
        // 验证friendName
        UserInfo friendInstance = userRepository.getByName(friendName);
        if (friendInstance == null) throw new BusinessException(EnumBusinessError.NOT_OUR_USER);
        int friendId = friendInstance.getUserId();
        // 获取我的信息
        UserInfo user = userRepository.getByUserId(id);
        String friendIds = user.getFriend_ids();
        JSONArray friendIdsArray = JSONArray.parseArray(friendIds);
        if (friendIdsArray == null) {
            friendIds = "[]";
            friendIdsArray = JSONArray.parseArray(friendIds);
        }
        for (int i = 0; i < friendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendIdsArray.get(i);
            System.out.println(afriend);
            if (friendId == (Integer)afriend.get("friendId")) {
                throw new BusinessException(EnumBusinessError.ALREADY_YOUR_FRIENDS_OR_WAIT);
            }
        }
        // 添加好友关系，置状态为待验证
        JSONObject newFriendObject = new JSONObject();
        newFriendObject.put("friendId", friendId);
        newFriendObject.put("status", UserInfoConstant.WAITING_PASSED);
        // 添加至原数组
        friendIdsArray.add(newFriendObject);
        String newFriendsJSONStr = friendIdsArray.toJSONString();
        // 写回数据库
        user.setFriend_ids(newFriendsJSONStr);
        UserInfo userResult = userRepository.save(user);

        // 获取好友信息
        UserInfo friend = userRepository.getByUserId(friendId);
        String friendfriendIds = friend.getFriend_ids();
        JSONArray friendfriendIdsArray = JSONArray.parseArray(friendfriendIds);
        if (friendfriendIdsArray == null) {
            friendfriendIds = "[]";
            friendfriendIdsArray = JSONArray.parseArray(friendfriendIds);
        }
        // 添加好友关系，置状态为待验证
        JSONObject newApplyObject = new JSONObject();
        newApplyObject.put("friendId", id);
        newApplyObject.put("status", UserInfoConstant.WAITING_DEAL);
        // 添加至原数组
        friendfriendIdsArray.add(newApplyObject);
        String newFriendFriendsJSONStr = friendfriendIdsArray.toJSONString();
        // 写回数据库
        friend.setFriend_ids(newFriendFriendsJSONStr);
        UserInfo friendResult = userRepository.save(friend);
        return userResult;
    }



    /**
     * @author xyy
     * @date 2020/1/25 20:10
    */
    @Override
    public UserInfo agreeForFriend(int id, int friendId) throws BusinessException {
        // 获取我的信息
        UserInfo user = userRepository.getByUserId(id);
        String friendIds = user.getFriend_ids();
        JSONArray friendIdsArray = JSONArray.parseArray(friendIds);
        if (friendIdsArray == null) {
            friendIds = "[]";
            friendIdsArray = JSONArray.parseArray(friendIds);
        }
        for (int i = 0; i < friendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendIdsArray.get(i);
            System.out.println(afriend);
            if (friendId == (Integer)afriend.get("friendId")) {
                // 删除原来的
                friendIdsArray.remove(i);
                // 组合新的
                JSONObject newFriendObject = new JSONObject();
                newFriendObject.put("friendId", friendId);
                newFriendObject.put("status", UserInfoConstant.FRIENDS);
                // 添加至原数组
                friendIdsArray.add(newFriendObject);
            }
        }
        String newFriendsJSONStr = friendIdsArray.toJSONString();
        // 写回数据库
        user.setFriend_ids(newFriendsJSONStr);
        UserInfo userResult = userRepository.save(user);

        // 获取好友信息
        UserInfo friend = userRepository.getByUserId(friendId);
        String friendFriendIds = friend.getFriend_ids();
        JSONArray friendFriendIdsArray = JSONArray.parseArray(friendFriendIds);
        if (friendFriendIdsArray == null) {
            friendFriendIds = "[]";
            friendFriendIdsArray = JSONArray.parseArray(friendFriendIds);
        }
        for (int i = 0; i < friendFriendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendFriendIdsArray.get(i);
            System.out.println(afriend);
            if (friendId == (Integer)afriend.get("friendId")) {
                // 删除原来的
                friendFriendIdsArray.remove(i);
                // 组合新的
                JSONObject newFriendObject = new JSONObject();
                newFriendObject.put("friendId", friendId);
                newFriendObject.put("status", UserInfoConstant.FRIENDS);
                // 添加至原数组
                friendFriendIdsArray.add(newFriendObject);
            }
        }
        String newFriendFriendsJSONStr = friendFriendIdsArray.toJSONString();
        // 写回数据库
        friend.setFriend_ids(newFriendFriendsJSONStr);
        UserInfo friendResult = userRepository.save(friend);
        return userResult;
    }



    /**
     * @author xyy
     * @date 2020/1/25 20:50
    */
    @Override
    public UserInfo deleteFriend(int id, int friendId) throws BusinessException {
        // 获取我的信息
        UserInfo user = userRepository.getByUserId(id);
        String friendIds = user.getFriend_ids();
        JSONArray friendIdsArray = JSONArray.parseArray(friendIds);
        if (friendIdsArray == null) {
            friendIds = "[]";
            friendIdsArray = JSONArray.parseArray(friendIds);
        }
        int flag = 0;
        for (int i = 0; i < friendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendIdsArray.get(i);
            System.out.println(afriend);
            if (friendId == (Integer)afriend.get("friendId") && UserInfoConstant.FRIENDS == afriend.get("status")) {
                // 删除原来的
                friendIdsArray.remove(i);
                flag = 1;
            }
        }
        if (flag == 0) throw new BusinessException(EnumBusinessError.NOT_YOUR_FRIEND);
        String newFriendsJSONStr = friendIdsArray.toJSONString();
        // 写回数据库
        user.setFriend_ids(newFriendsJSONStr);
        UserInfo userResult = userRepository.save(user);

        // 获取好友信息
        UserInfo friend = userRepository.getByUserId(friendId);
        String friendFriendIds = friend.getFriend_ids();
        JSONArray friendFriendIdsArray = JSONArray.parseArray(friendFriendIds);
        if (friendFriendIdsArray == null) {
            friendFriendIds = "[]";
            friendFriendIdsArray = JSONArray.parseArray(friendFriendIds);
        }
        for (int i = 0; i < friendFriendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendFriendIdsArray.get(i);
            System.out.println(afriend);
            if (friendId == (Integer)afriend.get("friendId") && UserInfoConstant.FRIENDS == afriend.get("status")) {
                // 删除原来的
                friendFriendIdsArray.remove(i);
            }
        }
        String newFriendFriendsJSONStr = friendFriendIdsArray.toJSONString();
        // 写回数据库
        friend.setFriend_ids(newFriendFriendsJSONStr);
        UserInfo friendResult = userRepository.save(friend);
        return userResult;
    }




    /**
     * @author xyy
     * @date 2020/1/26 10:51
    */
    @Override
    public UserInfo rejectForFriend(int id, int friendId) throws BusinessException {
        // 获取我的信息
        UserInfo user = userRepository.getByUserId(id);
        String friendIds = user.getFriend_ids();
        JSONArray friendIdsArray = JSONArray.parseArray(friendIds);
        if (friendIdsArray == null) {
            friendIds = "[]";
            friendIdsArray = JSONArray.parseArray(friendIds);
        }
        for (int i = 0; i < friendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendIdsArray.get(i);
            System.out.println(afriend);
            if (friendId == (Integer)afriend.get("friendId")) {
                // 删除原来的
                friendIdsArray.remove(i);
                // 组合新的
                JSONObject newFriendObject = new JSONObject();
                newFriendObject.put("friendId", friendId);
                newFriendObject.put("status", UserInfoConstant.REJECTED);
                // 添加至原数组
                friendIdsArray.add(newFriendObject);
            }
        }
        String newFriendsJSONStr = friendIdsArray.toJSONString();
        // 写回数据库
        user.setFriend_ids(newFriendsJSONStr);
        UserInfo userResult = userRepository.save(user);

        // 获取好友信息
        UserInfo friend = userRepository.getByUserId(friendId);
        String friendFriendIds = friend.getFriend_ids();
        JSONArray friendFriendIdsArray = JSONArray.parseArray(friendFriendIds);
        if (friendFriendIdsArray == null) {
            friendFriendIds = "[]";
            friendFriendIdsArray = JSONArray.parseArray(friendFriendIds);
        }
        for (int i = 0; i < friendFriendIdsArray.size(); i++) {
            JSONObject afriend = (JSONObject)friendFriendIdsArray.get(i);
            System.out.println(afriend);
            if (friendId == (Integer)afriend.get("friendId")) {
                // 删除原来的
                friendFriendIdsArray.remove(i);
                // 组合新的
                JSONObject newFriendObject = new JSONObject();
                newFriendObject.put("friendId", friendId);
                newFriendObject.put("status", UserInfoConstant.REJECTED);
                // 添加至原数组
                friendFriendIdsArray.add(newFriendObject);
            }
        }
        String newFriendFriendsJSONStr = friendFriendIdsArray.toJSONString();
        // 写回数据库
        friend.setFriend_ids(newFriendFriendsJSONStr);
        UserInfo friendResult = userRepository.save(friend);
        return userResult;
    }





    /**
     * @author xyy
     * @date 2020/2/10 13:07
    */
    @Override
    public ShowUserInfoDTO newAUser(UserInfo userInfo) throws BusinessException {
        userInfo.setOpenId(userInfo.getOpenId());

        UserInfo userInfoResult = userRepository.save(userInfo);

        ShowUserInfoDTO showUserInfoDTO = new ShowUserInfoDTO();
        showUserInfoDTO.setUserId(userInfoResult.getUserId());
        showUserInfoDTO.setName(userInfoResult.getName());
        showUserInfoDTO.setIcon_url(userInfoResult.getIcon_url());
        showUserInfoDTO.setFriend_ids(userInfoResult.getFriend_ids());
        showUserInfoDTO.setCreateTime(userInfoResult.getCreateTime());
        showUserInfoDTO.setModifyTime(userInfoResult.getModifyTime());

        return showUserInfoDTO;
    }

}
