package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.VStationTeamType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/27 19:37
 * @description
 */
public interface VStationTeamTypeRespository extends JpaRepository<VStationTeamType, Integer> {
    VStationTeamType getById(int id);
}
