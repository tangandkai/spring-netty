package com.tang.arrays;

import lombok.Data;

@Data
// 自定义类
class SortDTO {
    private String sortTarget;

    public SortDTO(String sortTarget) {
        this.sortTarget = sortTarget;
    }
}