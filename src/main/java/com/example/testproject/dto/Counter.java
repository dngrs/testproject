package com.example.testproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Counter {

    private long total;
    private long equalsSpecificValue;

}
