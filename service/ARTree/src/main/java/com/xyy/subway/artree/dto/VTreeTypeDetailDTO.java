package com.xyy.subway.artree.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xyy
 * @date 2020/2/18 14:41
 * @description
 */
@Data
public class VTreeTypeDetailDTO {
    @ApiModelProperty(value = "树的成长阶段", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "该阶段的最低高度", name = "height", example = "")
    private Integer height;

    @ApiModelProperty(value = "该阶段的最高高度", name = "nextHeight", example = "")
    private Integer nextHeight;

    @ApiModelProperty(value = "该阶段模型Url", name = "url", example = "")
    private String url;

    @ApiModelProperty(value = "神秘礼包触发概率", name = "probability1", example = "")
    private Float probability1;

    @ApiModelProperty(value = "普通礼包触发概率", name = "probability2", example = "")
    private Integer probability2;
}
