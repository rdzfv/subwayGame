package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.VStationStoreType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/27 14:59
 * @description
 */
public interface VStationStoreTypeRespository extends JpaRepository<VStationStoreType, Integer> {
    VStationStoreType getById(int id);
}
