package com.xyy.subway.trueword.model;

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
@ApiModel(value = "用户对象详情", description = "用户对象详情")
public class UserInfoDetail {

    @ApiModelProperty(value = "用户id", name = "id", example = "0")
    private Integer userId;

    @ApiModelProperty(value = "openId", name = "openId", example = "")
    private String openId;

    @ApiModelProperty(value = "游戏内的用户名", name = "name", example = "xyy")
    private String name;

    @ApiModelProperty(value = "游戏内的头像", name = "icon_url", example = "")
    private String icon_url;

    @ApiModelProperty(value = "sessionKey", name = "sessionKey", example = "")
    private String sessionKey;

    @ApiModelProperty(value = "token", name = "token", example = "")
    private String token;

    @ApiModelProperty(value = "好友ids", name = "friend_ids", example = "1,4,6,99")
    private String friend_ids;

    @CreatedDate
    @ApiModelProperty(value = "createTime", name = "createTime", example = "2020/01/23 14:56:00")
    private Date createTime;

    @LastModifiedDate
    @ApiModelProperty(value = "modifyTime", name = "createTime", example = "2020/01/23 14:56:00")
    private Date modifyTime;

    @ApiModelProperty(value = "code", name = "code", example = "")
    private String code;
}
