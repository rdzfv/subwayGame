package com.xyy.subway.game2d.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;

/**
 * @author xyy
 * @date 2020/1/31 14:05
 * @description
 */
@Data
@Scope("prototype")
public class ExpAndLevelDTO {
    Integer level;
    Long exp;
    Long nextLevelExp;
}
