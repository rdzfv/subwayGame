package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/23 15:49
 * @description
 */
@Data
@Entity
@Scope("prototype")
@ApiModel(value = "虚拟线路", description = "虚拟线路")
public class VRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "虚拟线路id", name = "id", example = "0")
    private Integer id;

    @ApiModelProperty(value = "虚拟线路名称", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "经过的虚拟地铁站Ids", name = "stationIds", example = "x,x,x")
    private String vstationIds;

    @ApiModelProperty(value = "建造者Id", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "清洁指数", name = "cleaness", example = "")
    private Integer cleaness;

    @ApiModelProperty(value = "安全指数", name = "security", example = "")
    private Integer security;

    @ApiModelProperty(value = "畅通指数", name = "uncrowedness", example = "")
    private Integer uncrowedness;

    @ApiModelProperty(value = "满意度", name = "satisfactionDegree", example = "")
    private Integer satisfactionDegree;

    @ApiModelProperty(value = "人流量", name = "satisfactionDegree", example = "")
    private Long visitorsFlowrate;
}
