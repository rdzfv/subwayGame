package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.TrainSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/26 13:14
 * @description
 */
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Integer> {
    TrainSchedule getById(int id);
}
