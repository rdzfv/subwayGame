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
 * @date 2020/1/23 15:23
 * @description
 */
@Data
@Entity
@ApiModel(value = "地铁站", description = "地铁站")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "地铁站编号（同八维通）", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "站点名称", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "所在路线ids", name = "routeIds", example = "")
    private String routeIds;

    @ApiModelProperty(value = "所在城市id", name = "cityId", example = "")
    private int cityId;
}
