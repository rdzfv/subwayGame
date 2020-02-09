package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Scope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author xyy
 * @date 2020/2/9 16:07
 * @description
 */
@Data
@Entity
@Scope("prototype")
@ApiModel(value = "其余设定细节", description = "其余设定细节")
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "其余设定细节编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "其余设定细节详情", name = "detail", example = "")
    private String detail;
}

