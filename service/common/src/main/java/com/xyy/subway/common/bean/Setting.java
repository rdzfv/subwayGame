package com.xyy.subway.common.bean;
import lombok.Data;

import java.io.Serializable;


/**
 * @author xyy
 * @date 2020/1/22 19:15
*/
@Data
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String value;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Setting other = (Setting) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}