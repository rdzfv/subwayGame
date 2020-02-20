package com.xyy.subway.artree.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author xyy
 * @date 2020/2/19 19:03
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "秘籍", description = "秘籍")
public class Gift2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "奖励编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "奖励金币", name = "content", example = "")
    private Integer money;

    @ApiModelProperty(value = "奖励经验", name = "content", example = "")
    private Integer exp;

    @ApiModelProperty(value = "奖励水滴", name = "content", example = "")
    private Integer water;

    @ApiModelProperty(value = "奖励水滴", name = "content", example = "")
    private String content;
}
