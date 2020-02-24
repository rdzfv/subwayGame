package com.xyy.subway.game2d.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author xyy
 * @date 2020/2/24 17:47
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "每日任务细节设定", description = "每日任务细节设定")
public class DailyTaskDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "每日任务设定编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "每日任务设定类型", name = "id", example = "")
    private Integer type;

    @ApiModelProperty(value = "每日任务设定细节", name = "id", example = "")
    private String content;
}
