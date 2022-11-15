package com.example.testproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyValuePair {

    private String key;
    private Object value;

}