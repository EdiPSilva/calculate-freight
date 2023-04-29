package br.com.java.calculatefreight.pageable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableDto {

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 10;

    public PageableDto(final Integer page, final Integer size, final Class klass, final String sortBy, final String sortDirection) {
        this.page = (page == null || page < 0) ? PAGE : page;
        this.size = (size == null || size < 1 || size > 10) ? SIZE : size;
        this.klass = klass;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    private Integer page;
    private Integer size;
    private Class klass;
    private String sortBy;
    private String sortDirection;
}
