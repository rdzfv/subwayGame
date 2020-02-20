package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/26 12:02
 * @description
 */
public interface RouteRespository extends JpaRepository<Route, Integer> {
    Route getById(int id);
}
