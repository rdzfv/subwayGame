package com.xyy.subway.artree.entity;

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
 * @date 2020/1/23 16:28
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "虚拟树", description = "虚拟树")
public class UserTree {
    @Id
    @ApiModelProperty(value = "种树用户编号", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "树的名字", name = "name", example = "")
    private String name;

    @ApiModelProperty(value = "树的成长阶段", name = "name", example = "")
    private Integer level;

    @ApiModelProperty(value = "下一阶段的高度", name = "name", example = "")
    private Integer nextHeight;

    @ApiModelProperty(value = "此时的url", name = "url", example = "")
    private String url;

    @ApiModelProperty(value = "树的成长值", name = "height", example = "")
    private Float height;

    @CreatedDate
    @ApiModelProperty(value = "种树时间", name = "createTime", example = "")
    private Date createTime;

    @LastModifiedDate
    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "")
    private Date updateTime;
}
