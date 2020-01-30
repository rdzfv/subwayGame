package com.xyy.subway.game2d.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyy
 * @date 2020/1/28 14:10
 * @description
 */
@Data
public class VRouteStationDTO {
    @ApiModelProperty(value = "用户id", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "虚拟路线id", name = "routeId", example = "")
    private Integer routeId;

    @ApiModelProperty(value = "虚拟路线Name", name = "routeName", example = "")
    private String routeName;

    @ApiModelProperty(value = "虚拟站点id", name = "routeStationId", example = "")
    private Integer routeStationId;

    @ApiModelProperty(value = "虚拟站点Name", name = "routeStationName", example = "")
    private String routeStationName;
}
