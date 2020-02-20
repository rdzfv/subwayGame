package com.xyy.subway.artree.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author xyy
 * @date 2020/2/18 15:16
 * @description
 */
@Data
public class UserTreeDTO {
    @ApiModelProperty(value = "种树用户编号", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "种子类型编号", name = "seedType", example = "")
    private Integer seedType;

    @ApiModelProperty(value = "树的名字", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "此时的url", name = "url", example = "")
    private String url;

    @ApiModelProperty(value = "树的高度", name = "height", example = "")
    private Float height;

    @ApiModelProperty(value = "下一阶段树的高度", name = "nextHeight", example = "")
    private Integer nextHeight;

    @ApiModelProperty(value = "种树时间", name = "createTime", example = "")
    private Date createTime;

    @ApiModelProperty(value = "更新时间", name = "id", example = "")
    private Date updateTime;
}
