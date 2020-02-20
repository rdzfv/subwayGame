package com.xyy.subway.artree.dao;

import com.xyy.subway.artree.entity.UserTreeOpRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author xyy
 * @date 2020/1/25 9:29
 * @description
 */
public interface UserTreeOpRecordRepository extends JpaRepository<UserTreeOpRecord, Integer> {
    UserTreeOpRecord getById(int id);
    List<UserTreeOpRecord> getAllByUserId2(int userId);
}
