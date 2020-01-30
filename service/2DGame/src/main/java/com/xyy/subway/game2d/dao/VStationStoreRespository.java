package com.xyy.subway.game2d.dao;

import com.xyy.subway.game2d.entity.VStation;
import com.xyy.subway.game2d.entity.VStationStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/27 14:49
 * @description
 */
public interface VStationStoreRespository extends JpaRepository<VStationStore, Integer> {
    VStationStore getById(int id);
    List<VStationStore> getAllByVstationId(int id);
}
