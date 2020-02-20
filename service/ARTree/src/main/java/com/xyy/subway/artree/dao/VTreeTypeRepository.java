package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.VTreeType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/2/18 14:03
 * @description
 */
public interface VTreeTypeRepository extends JpaRepository<VTreeType, Integer> {
    VTreeType getById(int id);
}
