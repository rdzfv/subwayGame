package com.xyy.subway.game2d.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyy
 * @date 2020/2/10 18:42
 * @description
 */
@Data
public class VBuildTeamTypeDetailDTO {
    @ApiModelProperty(value = "团队名字", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "等级", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "解锁等级", name = "level", example = "")
    private Integer unlockedIn;

    @ApiModelProperty(value = "解锁花费", name = "level", example = "")
    private Long cost;

    @ApiModelProperty(value = "解锁获得经验", name = "level", example = "")
    private Long upExp;

    @ApiModelProperty(value = "增加的人流量", name = "level", example = "")
    private Long visitor;

    @ApiModelProperty(value = "增加的小工数", name = "level", example = "")
    private Integer increaseWorker;

    @ApiModelProperty(value = "图片url", name = "level", example = "")
    private String picUrl;
}
