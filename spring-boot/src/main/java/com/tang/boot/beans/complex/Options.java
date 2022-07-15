package com.tang.boot.beans.complex;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Options {

    private List<Map<String, String>> orderBy;
    private Integer PageNum;
    private Integer RowNum;

    public List<Map<String, String>> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<Map<String, String>> orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPageNum() {
        return PageNum;
    }

    public void setPageNum(Integer pageNum) {
        PageNum = pageNum;
    }

    public Integer getRowNum() {
        return RowNum;
    }

    public void setRowNum(Integer rowNum) {
        RowNum = rowNum;
    }

    @Override
    public String toString() {
        return "Options{" +
                "orderBy=" + orderBy +
                ", PageNum=" + PageNum +
                ", RowNum=" + RowNum +
                '}';
    }
}
