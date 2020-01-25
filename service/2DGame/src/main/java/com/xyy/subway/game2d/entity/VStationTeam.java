package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xyy
 * @date 2020/1/23 16:03
 * @description
 */
@Data
@Entity
@ApiModel(value = "虚拟地铁站团队", description = "虚拟地铁站团队")
public class VStationTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "虚拟地铁站团队编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "虚拟地铁站id", name = "vstationId", example = "")
    private Integer vstationId;

    @ApiModelProperty(value = "虚拟地铁站团队名字", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "虚拟地铁站团队类型", name = "type", example = "")
    private Integer type;

    @ApiModelProperty(value = "虚拟地铁站团队等级", name = "type", example = "")
    private Integer level;
}
