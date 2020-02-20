package com.xyy.subway.artree.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/2/19 13:36
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "AR种树用户部分", description = "AR种树用户部分")
public class VTreeUser {
    @Id
    @ApiModelProperty(value = "用户编号", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "剩余可用水滴", name = "remainWater", example = "")
    private Integer remainWater;

    @ApiModelProperty(value = "对自己使用此数", name = "usedToMe", example = "")
    private Integer remainUsedToMe;

    @ApiModelProperty(value = "迫使产生变化", name = "change", example = "")
    private Integer change0;

    @CreatedDate
    @ApiModelProperty(value = "创建时间", name = "createTime", example = "")
    private Date createTime;

    @LastModifiedDate
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "")
    private Date updateTime;
}