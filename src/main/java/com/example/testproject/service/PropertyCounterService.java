package com.example.testproject.service;

import com.example.testproject.dto.Counter;

public interface PropertyCounterService {

    Counter countField(String pathToJsonFile, String field, String value);

}
