package com.xyy.subway.trueword.httpdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * @author     ：xyy
 * @date       ：Created in 2019/12/20
 * @description：
 * @version:     1.0.0
 */

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse {
    private int code;
    private String message;
}
