package com.xyy.subway.trueword.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/29 14:46
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "用户信息更新对象详情", description = "用户信息对象详情")
public class UpdateUserInfoDTO {
    @ApiModelProperty(value = "用户id", name = "userId", example = "0")
    private Integer userId;

    @ApiModelProperty(value = "是否是新用户", name = "isNewUser", example = "0")
    private Integer isNewUser;

    @ApiModelProperty(value = "游戏内的用户名", name = "name", example = "xyy")
    private String name;

    @ApiModelProperty(value = "游戏内的头像", name = "icon_url", example = "")
    private String icon_url;

}
