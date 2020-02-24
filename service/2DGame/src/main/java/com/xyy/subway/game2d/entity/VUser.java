package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/23 15:36
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "虚拟用户", description = "虚拟用户")
public class VUser {
    @Id
    @ApiModelProperty(value = "用户id", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "经验值", name = "exp", example = "")
    private Long exp;

    @ApiModelProperty(value = "等级", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "金币", name = "exp", example = "")
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

    @ApiModelProperty(value = "拥有小工总数", name = "workers", example = "")
    private Integer workers;

    @ApiModelProperty(value = "可用小工总数", name = "availableWorkers", example = "")
    private Integer availableWorkers;

    @ApiModelProperty(value = "连续登录天数", name = "loginDays", example = "")
    private Integer loginDays;

    @ApiModelProperty(value = "强制修改位", name = "loginDays", example = "")
    private Integer change0;

    @CreatedDate
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "")
    private Date createTime;

    @LastModifiedDate
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "")
    private Date updateTime;
}
