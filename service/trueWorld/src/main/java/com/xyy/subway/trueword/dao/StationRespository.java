package com.xyy.subway.trueword.dao;

import com.xyy.subway.trueword.entity.City;
import com.xyy.subway.trueword.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/26 12:17
 * @description
 */
public interface StationRespository extends JpaRepository<Station, String> {
    Station getById(String id);
    Station getByName(String name);
}
