package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.DailyTask;
import com.xyy.subway.game2d.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xyy
 * @date 2020/2/24 14:36
 * @description
 */
public interface DailyTaskRespository extends JpaRepository<DailyTask, Integer> {
    List<DailyTask> getAllByUserId(int userId);
}
