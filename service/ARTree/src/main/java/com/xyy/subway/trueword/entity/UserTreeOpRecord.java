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
 * @date 2020/1/23 16:36
 * @description
 */
@Data
@Entity
@ApiModel(value = "对虚拟树的操作记录", description = "对虚拟树的操作记录")
public class UserTreeOpRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "对虚拟树的操作记录编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "操作用户编号", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "操作类型", name = "type", example = "")
    private Integer type;

    @ApiModelProperty(value = "操作内容", name = "content", example = "")
    private Integer content;

    @ApiModelProperty(value = "操作时间", name = "time", example = "")
    private Date time;
}
