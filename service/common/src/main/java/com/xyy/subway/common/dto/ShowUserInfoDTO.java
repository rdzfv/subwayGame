package com.xyy.subway.common.dto;

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
 * @date 2020/1/25 14:09
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "用户展示对象详情", description = "用户展示对象详情")
public class ShowUserInfoDTO {

    @ApiModelProperty(value = "用户id", name = "id", example = "0")
    private Integer userId;

    @ApiModelProperty(value = "游戏内的用户名", name = "name", example = "xyy")
    private String name;

    @ApiModelProperty(value = "游戏内的头像", name = "icon_url", example = "")
    private String icon_url;

    @ApiModelProperty(value = "好友ids", name = "friend_ids", example = "1,4,6,99")
    private String friend_ids;

    @CreatedDate
    @ApiModelProperty(value = "createTime", name = "createTime", example = "2020/01/23 14:56:00")
    private Date createTime;

    @LastModifiedDate
    @ApiModelProperty(value = "modifyTime", name = "createTime", example = "2020/01/23 14:56:00")
    private Date modifyTime;
}
