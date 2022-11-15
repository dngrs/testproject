package com.example.testproject.service;

import com.example.testproject.dto.KeyValuePair;

import java.util.List;

public interface JsonParserService {

    List<KeyValuePair> parseJsonFromFile(String pathToJsonFile);

}
