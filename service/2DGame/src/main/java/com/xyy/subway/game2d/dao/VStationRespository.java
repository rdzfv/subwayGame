package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.entity.VUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/27 13:53
 * @description
 */
public interface VStationRespository extends JpaRepository<VStation, Integer> {
    VStation getById(int id);
}
