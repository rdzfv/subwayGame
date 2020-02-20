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
 * @date 2020/2/19 15:39
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "秘籍", description = "秘籍")
public class Tips {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "秘籍编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "秘籍内容", name = "content", example = "")
    private String content;
}
