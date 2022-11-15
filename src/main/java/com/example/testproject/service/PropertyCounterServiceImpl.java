package com.example.testproject.service;

import com.example.testproject.dto.Counter;
import com.example.testproject.dto.KeyValuePair;
import com.example.testproject.utils.TypeConvertorUtils;

import java.util.List;
import java.util.function.Predicate;

public class PropertyCounterServiceImpl implements PropertyCounterService {

    private JsonParserService jsonParserService;

    public PropertyCounterServiceImpl(JsonParserService jsonParserService) {
        this.jsonParserService = jsonParserService;
    }

    public Counter countField(String pathToJsonFile, String field, String value) {
        List<KeyValuePair> list = jsonParserService.parseJsonFromFile(pathToJsonFile);
        Object expectedValue = TypeConvertorUtils.getTypedInputParameter(value);
        long totalCounter = countFieldsByCondition(list, (k) -> field.equals(k.getKey()));
        long equalCounter = countFieldsByCondition(list, (k) -> field.equals(k.getKey()) &&
                (k.getValue() == null || k.getValue().equals(expectedValue)));
        return new Counter(totalCounter, equalCounter);
    }

    private long countFieldsByCondition(List<KeyValuePair> list, Predicate<KeyValuePair> condition) {
        return list.stream()
                .filter(condition)
                .count();
    }

}
