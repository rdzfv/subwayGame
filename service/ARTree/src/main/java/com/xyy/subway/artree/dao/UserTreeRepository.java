package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.UserTree;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xyy
 * @date 2020/2/18 14:00
 * @description
 */
public interface UserTreeRepository extends JpaRepository<UserTree, Integer> {
    UserTree getByUserId(int userrId);
}
