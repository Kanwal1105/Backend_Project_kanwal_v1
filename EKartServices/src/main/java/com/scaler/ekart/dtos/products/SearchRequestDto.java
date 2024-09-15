package com.scaler.ekart.dtos.products;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SearchRequestDto {
    private String query;

    private Integer pageLimit;

    private Integer pageNumber;

    private List<SortParam> sortParamList = new ArrayList<>();
}
