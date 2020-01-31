package com.xyy.subway.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/25 16:36
 * @description
 */
@Data
public class FriendsDTO {

    @ApiModelProperty(value = "用户id", name = "id", example = "0")
    private Integer userId;

    @ApiModelProperty(value = "游戏内的用户名", name = "name", example = "xyy")
    private String name;

    @ApiModelProperty(value = "游戏内的头像", name = "icon_url", example = "")
    private String icon_url;

    @ApiModelProperty(value = "好友关系状态", name = "status", example = "")
    private String status;

    @CreatedDate
    @ApiModelProperty(value = "createTime", name = "createTime", example = "2020/01/23 14:56:00")
    private Date createTime;
}
