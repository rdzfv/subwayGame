package com.xyy.subway.artree.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xyy
 * @date 2020/1/23 16:36
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@ApiModel(value = "对虚拟树的操作记录", description = "对虚拟树的操作记录")
public class UserTreeOpRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "对虚拟树的操作记录编号", name = "id", example = "")
    private Integer id;

    @ApiModelProperty(value = "操作用户编号1", name = "userId1", example = "")
    private Integer userId1;

    @ApiModelProperty(value = "操作用户编号2", name = "userId2", example = "")
    private Integer userId2;

    @CreatedDate
    @ApiModelProperty(value = "操作时间", name = "time", example = "")
    private Date create_time;
}
