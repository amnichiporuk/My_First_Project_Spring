package com.example.dev_j310nam.filtering;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Filters {

    private String filterType;
    private String filterNameAddress;


}
