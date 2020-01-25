package com.xyy.subway.artree.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/23 15:50
 * @description
 */
@Data
@Entity
@ApiModel(value = "车次", description = "车次")
public class TrainSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "车次id", name = "id", example = "0")
    private Integer id;

    @ApiModelProperty(value = "发车时间", name = "startTime", example = "2020/01/23 15:02:00")
    private Date startTime;

    @ApiModelProperty(value = "路线id", name = "id", example = "0")
    private Integer routeId;
}
