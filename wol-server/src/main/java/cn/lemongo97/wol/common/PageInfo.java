package cn.lemongo97.wol.common;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author lemongo97
 */
@Data
public class PageInfo {
    private Integer pageSize = 10;
    private Integer pageNumber = 1;

    public Pageable getPageable() {
        return PageRequest.of(this.pageNumber - 1, this.pageSize);
    }

    public static PageInfo create(){
        return new PageInfo();
    }
}
