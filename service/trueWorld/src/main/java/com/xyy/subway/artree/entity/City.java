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
 * @date 2020/1/23 15:18
 * @description
 */
@Data
@Entity
@ApiModel(value = "城市", description = "城市")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "城市编号（同八维通）", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "城市名称", name = "name", example = "")
    private String name;
}
