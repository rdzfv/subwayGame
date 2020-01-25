package com.xyy.subway.trueword.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xyy
 * @date 2020/1/23 15:36
 * @description
 */
@Data
@Entity
@ApiModel(value = "虚拟用户", description = "虚拟用户")
public class VUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户id", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "经验值", name = "exp", example = "")
    private Long exp;

    @ApiModelProperty(value = "等级", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "经验值", name = "exp", example = "")
    private Long money;

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
