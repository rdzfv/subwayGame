package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.VUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/1/25 9:29
 * @description
 */
public interface VuserRepository extends JpaRepository<VUser, Integer> {
    VUser getByUserId(int id);
}
