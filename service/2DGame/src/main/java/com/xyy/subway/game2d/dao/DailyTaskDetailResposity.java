package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.DailyTask;
import com.xyy.subway.game2d.entity.DailyTaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/24 17:53
 * @description
 */
public interface DailyTaskDetailResposity extends JpaRepository<DailyTaskDetail, Integer> {
    DailyTaskDetail getById(int id);
}
