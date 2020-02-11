package com.xyy.subway.game2d.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyy
 * @date 2020/2/11 13:34
 * @description
 */
@Data
public class VTeamTypeDetailDTO {
    @ApiModelProperty(value = "团队名字", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "等级", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "解锁等级", name = "unlockedIn", example = "")
    private Integer unlockedIn;

    @ApiModelProperty(value = "解锁花费", name = "cost", example = "")
    private Long cost;

    @ApiModelProperty(value = "解锁获得经验", name = "upExp", example = "")
    private Long upExp;

    @ApiModelProperty(value = "增加的人流量", name = "visitor", example = "")
    private Long visitor;

    @ApiModelProperty(value = "增加的清洁指数", name = "increaseTidy", example = "")
    private Integer increaseTidy;

    @ApiModelProperty(value = "增加的安全指数", name = "increaseSafe", example = "")
    private Integer increaseSafe;

    @ApiModelProperty(value = "增加的畅通指数", name = "increaseUncrowded", example = "")
    private Integer increaseUncrowded;

    @ApiModelProperty(value = "图片url", name = "picUrl", example = "")
    private String picUrl;
}
