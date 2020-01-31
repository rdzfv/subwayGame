package com.xyy.subway.common.dao;

import com.xyy.subway.common.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/25 9:29
 * @description
 */
public interface UserRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo getByUserId(int id);
    UserInfo getByOpenId(String openId);
    UserInfo getByName(String name);
}
