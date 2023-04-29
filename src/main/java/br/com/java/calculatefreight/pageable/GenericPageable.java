package br.com.java.calculatefreight.pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class GenericPageable {

    @Autowired
    GenericSort genericSort;

    public Pageable buildPageable(final PageableDto pageableDto) {
        return PageRequest.of(pageableDto.getPage(), pageableDto.getSize(), genericSort.buildSort(pageableDto.getKlass(), pageableDto.getSortBy(), pageableDto.getSortDirection()));
    }
}
