package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.Detail;
import com.xyy.subway.game2d.entity.VRoute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/2/9 16:37
 * @description
 */
public interface DetailRespository extends JpaRepository<Detail, Integer> {
    Detail getById(int id);
}
