package com.xyy.subway.trueword.dao;

import com.xyy.subway.trueword.entity.UserInfo;
import com.xyy.subway.trueword.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/26 11:25
 * @description
 */
public interface UserScheduleRepository extends JpaRepository<UserSchedule, Integer> {
    UserSchedule getById(int id);
}
