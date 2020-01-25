package com.xyy.subway.artree.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xyy
 * @date 2020/1/23 15:27
 * @description
 */
@Data
@Entity
@ApiModel(value = "线路", description = "线路")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "线路编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "线路名称", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "经过的地铁站Ids", name = "stationIds", example = "1,2,5")
    private String stationIds;
}
