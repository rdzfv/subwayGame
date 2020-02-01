package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/23 16:21
 * @description
 */
@Data
@Entity
@ApiModel(value = "虚拟地铁站商店", description = "虚拟地铁站商店")
public class VStationStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "虚拟地铁站商店编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "虚拟地铁站id", name = "vstationId", example = "")
    private Integer vstationId;

    @ApiModelProperty(value = "虚拟地铁站商店名称", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "虚拟地铁站商店类型", name = "type", example = "")
    private Integer type;

    @ApiModelProperty(value = "虚拟地铁站商店图片", name = "type", example = "")
    private String url;

    @ApiModelProperty(value = "虚拟地铁站商店等级", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "虚拟地铁站商店位置", name = "name", example = "1,3")
    private Integer position;

    @ApiModelProperty(value = "虚拟地铁站商店状态", name = "status", example = "")
    private Integer status;

    @ApiModelProperty(value = "建造剩余时间", name = "status", example = "")
    private Long remainTime;

    @ApiModelProperty(value = "最大存储金币", name = "maxProfit", example = "")
    private Float maxProfit;

    @ApiModelProperty(value = "已获得金币", name = "availableProfitt", example = "")
    private Float availableProfit;
}
