package com.xyy.subway.game2d.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Scope;

import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/28 14:28
 * @description
 */
@Data
@Scope("prototype")
public class VStationStoreDTO {
    @ApiModelProperty(value = "虚拟地铁站商店编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "虚拟地铁站商店名称", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "虚拟地铁站商店类型名称", name = "type", example = "")
    private String type;

    @ApiModelProperty(value = "虚拟地铁站商店图片地址", name = "type", example = "")
    private String picUrl;

    @ApiModelProperty(value = "虚拟地铁站商店等级", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "虚拟地铁站商店位置", name = "name", example = "1,3")
    private Integer position;

    @ApiModelProperty(value = "虚拟地铁站商店状态", name = "status", example = "")
    private Integer status;

    @ApiModelProperty(value = "建造剩余时间", name = "status", example = "")
    private Long remainTime;

    @ApiModelProperty(value = "升级策略", name = "status", example = "")
    private String levelUpJSON;

    @ApiModelProperty(value = "最大存储金币", name = "maxProfit", example = "")
    private Float maxProfit;

    @ApiModelProperty(value = "已获得金币", name = "availableProfitt", example = "")
    private Float availableProfit;

    @ApiModelProperty(value = "是否已被删除", name = "isDeleted", example = "")
    private Integer isDeleted;

    @ApiModelProperty(value = "是否有升级操作", name = "isLevelup", example = "")
    private Integer isLevelup;
}
