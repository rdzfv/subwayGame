package com.xyy.subway.game2d.dto;

import lombok.Data;

/**
 * @author xyy
 * @date 2020/1/31 14:05
 * @description
 */
@Data
public class ExpAndLevelDTO {
    Integer level;
    Long exp;
    Long nextLevelExp;
}
