package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.DailyTask;
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
