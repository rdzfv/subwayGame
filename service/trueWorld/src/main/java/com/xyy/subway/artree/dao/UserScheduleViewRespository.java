package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.view.UserScheduleView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/26 15:33
 * @description
 */
public interface UserScheduleViewRespository extends JpaRepository<UserScheduleView, Integer> {
    UserScheduleView getById(int id);
    List<UserScheduleView> getAllByUserId(int id);
}
