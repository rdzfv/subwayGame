package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.UserTreeOpRecord;
import com.xyy.subway.artree.entity.VTreeUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/2/19 13:43
 * @description
 */
public interface VTreeUserRepository extends JpaRepository<VTreeUser, Integer> {
    VTreeUser getByUserId(int userId);
}
