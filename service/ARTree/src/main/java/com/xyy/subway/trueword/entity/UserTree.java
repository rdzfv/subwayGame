package com.xyy.subway.trueword.entity;

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
 * @date 2020/1/23 16:28
 * @description
 */
@Data
@Entity
@ApiModel(value = "虚拟树", description = "虚拟树")
public class UserTree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "树编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "种树用户编号", name = "id", example = "")
    private Integer userId;

    @ApiModelProperty(value = "种树站点编号", name = "id", example = "")
    private Integer stationId;

    @ApiModelProperty(value = "种子类型编号", name = "id", example = "")
    private Integer seedType;

    @ApiModelProperty(value = "树的名字", name = "id", example = "")
    private String name;

    @ApiModelProperty(value = "树的成长值", name = "id", example = "")
    private Integer score;

    @ApiModelProperty(value = "种树时间", name = "id", example = "")
    private Date createTime;

    @ApiModelProperty(value = "更新时间", name = "id", example = "")
    private Date updateTime;
}
