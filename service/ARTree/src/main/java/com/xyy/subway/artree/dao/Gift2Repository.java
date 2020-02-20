package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.Gift2;
import com.xyy.subway.artree.entity.Tips;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/2/19 19:32
 * @description
 */
public interface Gift2Repository extends JpaRepository<Gift2, Integer> {
    Gift2 getById(int id);
}
