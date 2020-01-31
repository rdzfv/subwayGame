package com.xyy.subway.common.service;


import com.xyy.subway.common.dto.FriendsDTO;
import com.xyy.subway.common.dto.ShowUserInfoDTO;
import com.xyy.subway.common.dto.UpdateUserInfoDTO;
import com.xyy.subway.common.entity.UserInfo;
import com.xyy.subway.common.error.BusinessException;
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
    UserInfo login(String openId);
    ShowUserInfoDTO updateUserInfo(UpdateUserInfoDTO showUserInfoDTO);
    ArrayList<FriendsDTO> listMyFriends(int id);
    UserInfo applyForFriend(int id, String friendName) throws BusinessException;
    UserInfo agreeForFriend(int id, int friendId) throws BusinessException;
    UserInfo deleteFriend(int id, int friendId) throws BusinessException;
    UserInfo rejectForFriend(int id, int friendId) throws BusinessException;
}
