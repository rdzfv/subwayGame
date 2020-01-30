package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.VRoute;
import com.xyy.subway.game2d.entity.VStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/27 14:39
 * @description
 */
public interface VRouteRespository extends JpaRepository<VRoute, Integer> {
    VRoute getById(int id);
    List<VRoute> getAllByUserId(int id);
}
