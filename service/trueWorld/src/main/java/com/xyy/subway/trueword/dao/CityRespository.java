package com.xyy.subway.trueword.dao;

import com.xyy.subway.trueword.entity.City;
import com.xyy.subway.trueword.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author xyy
 * @date 2020/1/26 11:51
*/
public interface CityRespository extends JpaRepository<City, Integer> {
    City getById(int id);
}
