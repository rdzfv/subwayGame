package com.xyy.subway.trueword.service;


import com.xyy.subway.trueword.dto.FriendsDTO;
import com.xyy.subway.trueword.dto.ShowUserInfoDTO;
import com.xyy.subway.trueword.dto.UpdateUserInfoDTO;
import com.xyy.subway.trueword.entity.UserInfo;
import com.xyy.subway.trueword.error.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author xyy
 * @date 2020/1/25 9:21
 * @description
 */
@Service
public interface UserService {
    UserInfo getUserInfoById(int id) throws BusinessException;
    ShowUserInfoDTO login(String openId) throws BusinessException;
    ShowUserInfoDTO updateUserInfo(UpdateUserInfoDTO showUserInfoDTO);
    ArrayList<FriendsDTO> listMyFriends(int id);
    UserInfo applyForFriend(int id, String friendName) throws BusinessException;
    UserInfo agreeForFriend(int id, int friendId) throws BusinessException;
    UserInfo deleteFriend(int id, int friendId) throws BusinessException;
    UserInfo rejectForFriend(int id, int friendId) throws BusinessException;
    ShowUserInfoDTO newAUser(UserInfo userInfo) throws BusinessException;
}
