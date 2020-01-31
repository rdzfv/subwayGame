package com.xyy.subway.common.httpdto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;


/**
 * @author     ：xyy
 * @date       ：Created in 2019/12/20
 * @description：
 * @version:     1.0.0
 */

@Data
@NoArgsConstructor
public class ObjectCollectionResponse<T> extends BaseResponse {
    private Collection<T> data;
    private long total;
    private long size;

    public ObjectCollectionResponse(Collection<T> data, long total, long size) {
        super(200, "OK");
        this.data = data;
        this.total = total;
        this.size = size;
    }

    @SuppressWarnings("unchecked")
    public ObjectCollectionResponse(IPage page) {
        super(200, "OK");
        this.data = page.getRecords();
        this.total = page.getTotal();
        this.size = page.getSize();
    }

    public ObjectCollectionResponse(Collection<T> data) {
        super(200, "OK");
        this.data = data;
        if (data != null) {
            this.total = data.size();
            this.size = Integer.valueOf(String.valueOf(this.total));
        }
    }

    @Deprecated
    public static <T> ObjectCollectionResponse<T> errorResponse(int code, String msg) {

        ObjectCollectionResponse<T> response = new ObjectCollectionResponse<>();
        response.setCode(code);
        response.setMessage(msg);
        response.data = null;
        response.total = 0L;
        return response;
    }

    @Deprecated
    public static <T> ObjectCollectionResponse<T> errorResponse(int code) {
        return errorResponse(code, MsgCodeUtils.getCodeMessage(code));
    }



    public ObjectCollectionResponse(org.springframework.data.domain.Page<T> data) {
        super(200, "OK");
        this.data = data.getContent();
        this.total = data.getTotalElements();
        this.size = data.getSize();
    }
}
