package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xyy
 * @date 2020/1/23 16:27
 * @description
 */
@Data
@Entity
@ApiModel(value = "虚拟地铁站商店类型", description = "虚拟地铁站商店类型")
public class VStationStoreType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "虚拟地铁站商店类型编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "虚拟地铁站商店类型名称", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "虚拟地铁站商店类型详情", name = "detail", example = "")
    private String detail;
}
