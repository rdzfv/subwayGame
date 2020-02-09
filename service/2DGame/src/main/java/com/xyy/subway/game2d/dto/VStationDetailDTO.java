package com.xyy.subway.game2d.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Scope;

/**
 * @author xyy
 * @date 2020/1/28 14:21
 * @description
 */
@Data
@Scope("prototype")
public class VStationDetailDTO {
    @ApiModelProperty(value = "虚拟地铁站编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "虚拟站点名称", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "所在虚拟路线ids", name = "routeIds", example = "")
    private String routeIds;

    @ApiModelProperty(value = "清洁指数", name = "cleaness", example = "")
    private Integer cleaness;

    @ApiModelProperty(value = "安全指数", name = "security", example = "")
    private Integer security;

    @ApiModelProperty(value = "畅通指数", name = "uncrowedness", example = "")
    private Integer uncrowedness;

    @ApiModelProperty(value = "满意度", name = "satisfactionDegree", example = "")
    private Integer satisfactionDegree;

    @ApiModelProperty(value = "人流量", name = "satisfactionDegree", example = "")
    private Long visitorFlowrate;




}
