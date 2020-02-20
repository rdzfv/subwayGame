package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.Tips;
import com.xyy.subway.artree.entity.UserTreeOpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/2/19 15:42
 * @description
 */
public interface TipsRepository extends JpaRepository<Tips, Integer> {
}
