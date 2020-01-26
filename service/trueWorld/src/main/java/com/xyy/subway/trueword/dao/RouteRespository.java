package com.xyy.subway.trueword.dao;

import com.xyy.subway.trueword.entity.City;
import com.xyy.subway.trueword.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/26 12:02
 * @description
 */
public interface RouteRespository extends JpaRepository<Route, Integer> {
    Route getById(int id);
}
