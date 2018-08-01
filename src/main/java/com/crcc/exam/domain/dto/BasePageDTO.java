package com.crcc.exam.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
@ToString
@Slf4j
public class BasePageDTO<T> implements Page<T> {

    /**
     * 查询条件
     */
    @Setter
    @Getter
    private T condition;

    @Setter
    private int number;

    @Setter
    private int size;

    @Setter
    private int totalPages;

    @Setter
    private int totalElements;

    @Setter
    private int numberOfElements;

    @Setter
    private List<T> content;

    private Sort sort;

    @Override
    public int getTotalPages() {
        return this.totalPages;
    }

    @Override
    public long getTotalElements() {
        return this.totalElements;
    }

    @Override
    public Page map(Function function) {
        return null;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getNumberOfElements() {
        return this.numberOfElements;
    }

    @Override
    public List getContent() {
        return this.content;
    }

    @Override
    public boolean hasContent() {
        return false;
    }


    @JsonDeserialize(using = com.crcc.exam.util.SortDeserializer.class)
    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public boolean isFirst() {
        return false;
    }

    @Override
    public boolean isLast() {
        return false;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public Pageable nextPageable() {
        return null;
    }

    @Override
    public Pageable previousPageable() {
        return null;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

}
