package com.xyy.subway.trueword.entity.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/26 13:38
 * @description
 */
@Entity
@Data
@ApiModel(value = "用户行程", description = "用户行程")
public class UserScheduleView {
    @Id
    @ApiModelProperty(value = "行程id", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "所在城市id", name = "cityId", example = "")
    private Integer cityId;

    @ApiModelProperty(value = "所在城市Name", name = "cityName", example = "")
    private String cityName;

    @ApiModelProperty(value = "出发时间", name = "startTime", example = "2020/01/23 15:02:00")
    private Date startTime;

    @ApiModelProperty(value = "出发站点Id", name = "startStationId", example = "")
    private String startStationId;

    @ApiModelProperty(value = "出发站点Name", name = "startStationName", example = "")
    private String startStationName;

    @ApiModelProperty(value = "结束时间", name = "endTime", example = "2020/01/23 15:02:00")
    private Date endTime;

    @ApiModelProperty(value = "结束站点Id", name = "endStationId", example = "")
    private String endStationId;

    @ApiModelProperty(value = "结束站点Name", name = "endStationName", example = "")
    private String endStationName;

    @ApiModelProperty(value = "用户id", name = "userId", example = "")
    private Integer userId;

    @ApiModelProperty(value = "用户name", name = "userName", example = "")
    private String userName;

    @ApiModelProperty(value = "行程状态", name = "status", example = "1表示进行中（入关就是进行中）；2表示未付款（出关即把状态改为未付款）；3表示已结束（付款操作异步进行，付款完成推送消息，修改状态为已结束）")
    private Integer status;

    @ApiModelProperty(value = "花费", name = "fee", example = "")
    private Float fee;
}
