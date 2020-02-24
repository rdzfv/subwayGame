package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/2/24 14:23
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "每日任务", description = "每日任务")
public class DailyTask {
    @Id
    @ApiModelProperty(value = "用户id", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "任务一id", name = "task1", example = "")
    private Integer task1;

    @ApiModelProperty(value = "任务一完成情况", name = "todo1", example = "")
    private Integer todo1;

    @ApiModelProperty(value = "任务二id", name = "task2", example = "")
    private Integer task2;

    @ApiModelProperty(value = "任务二完成情况", name = "todo2", example = "")
    private Integer todo2;

    @ApiModelProperty(value = "任务三id", name = "task3", example = "")
    private Integer task3;

    @ApiModelProperty(value = "任务三完成情况", name = "todo3", example = "")
    private Integer todo3;

    @ApiModelProperty(value = "任务四id", name = "task4", example = "")
    private Integer task4;

    @ApiModelProperty(value = "任务四完成情况", name = "todo4", example = "")
    private Integer todo4;

    @ApiModelProperty(value = "任务五id", name = "task5", example = "")
    private Integer task5;

    @ApiModelProperty(value = "任务五完成情况", name = "todo5", example = "")
    private Integer todo5;

    @CreatedDate
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "")
    private Date createTime;

    @LastModifiedDate
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "")
    private Date updateTime;
}
