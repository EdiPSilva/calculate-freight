package br.com.java.calculatefreight.pageable;

import br.com.java.calculatefreight.configuration.MessageCodeEnum;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericSort {

    private static final String DEFAULT_SORT_BY = "id";

    @Autowired
    MessageConfiguration messageConfiguration;

    private List<String> getFields(Class klass) {
        return Arrays.stream(klass.getDeclaredFields()).map(field -> field.getName()).collect(Collectors.toList());
    }

    public Sort buildSort(final Class klass, final String sortBy, final String sortDirection) {
        return buildSort(validateAttributes(klass, sortBy), validateSortDirection(sortDirection));
    }

    private String validateAttributes(final Class klass, final String value) {
        if(value == null || value.trim().isEmpty()) {
            return DEFAULT_SORT_BY;
        }
        final List<String> attributes = getFields(klass);
        if (!attributes.contains(value.trim())) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.COLUMN_NOT_FOUND), HttpStatus.BAD_REQUEST);
        }
        return value.trim();
    }

    private Sort.Direction validateSortDirection(final String value) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if ((value != null && !value.trim().isEmpty()) && Sort.Direction.DESC.equals(value.trim().toUpperCase())) {
            sortDirection = Sort.Direction.DESC;
        }
        return sortDirection;
    }

    private Sort buildSort(final String sortBy, final Sort.Direction sortDirection) {
        return sortDirection.isAscending() ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    }
}
