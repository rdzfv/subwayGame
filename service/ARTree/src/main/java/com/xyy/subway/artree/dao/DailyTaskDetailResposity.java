package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.DailyTaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/2/24 17:53
 * @description
 */
public interface DailyTaskDetailResposity extends JpaRepository<DailyTaskDetail, Integer> {
    DailyTaskDetail getById(int id);
}
