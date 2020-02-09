package com.xyy.subway.game2d.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyy
 * @date 2020/2/9 16:24
 * @description
 */
@Data
public class StationBuildDetailDTO {
    @ApiModelProperty(value = "地铁站个数序号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "解锁花费", name = "cost", example = "")
    private float cost;

    @ApiModelProperty(value = "解锁等级", name = "unLockedIn", example = "")
    private String unLockedIn;

    @ApiModelProperty(value = "解锁获得的经验", name = "exp", example = "")
    private long exp;
}
