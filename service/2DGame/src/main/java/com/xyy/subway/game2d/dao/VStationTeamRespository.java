package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.VStationTeam;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/27 15:07
 * @description
 */
public interface VStationTeamRespository extends JpaRepository<VStationTeam, Integer> {
    VStationTeam getById(int id);
}
