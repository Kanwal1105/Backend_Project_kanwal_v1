package com.scaler.ekart.dtos.products;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SortParam {
    private String paramName;

    private SortType sortType;
}
