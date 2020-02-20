package com.xyy.subway.game2d.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author xyy
 * @date 2020/2/9 15:22
 * @description
 */
@Data
public class StoreUpLevelUpDetailDTO {
    @ApiModelProperty(value = "店铺类型", name = "type", example = "")
    private Integer type;

    @ApiModelProperty(value = "店铺等级", name = "level", example = "")
    private Integer level;

    @ApiModelProperty(value = "解锁等级", name = "unlockedIn", example = "")
    private Integer unlockedIn;

    @ApiModelProperty(value = "解锁花费", name = "cost", example = "")
    private Integer cost;

    @ApiModelProperty(value = "解锁收益/秒", name = "profit", example = "")
    private float profit;

    @ApiModelProperty(value = "最大存储收益", name = "maxProfit", example = "")
    private float maxProfit;

    @ApiModelProperty(value = "解锁获得得的经验", name = "upExp", example = "")
    private long upExp;

    @ApiModelProperty(value = "解锁变化的畅通指数", name = "unCrowdedness", example = "")
    private Integer unCrowdedness;

    @ApiModelProperty(value = "解锁变化的安全指数", name = "safety", example = "")
    private Integer safety;

    @ApiModelProperty(value = "解锁变化的整洁指数", name = "tidy", example = "")
    private Integer tidy;

    @ApiModelProperty(value = "建造所需的小工数", name = "worker", example = "")
    private Integer worker;

    @ApiModelProperty(value = "解锁新增人流量", name = "visitor", example = "")
    private Integer visitor;

    @ApiModelProperty(value = "建造所需时间", name = "building_time", example = "")
    private long building_time;

    @ApiModelProperty(value = "图片url", name = "picUrl", example = "")
    private String picUrl;
}
