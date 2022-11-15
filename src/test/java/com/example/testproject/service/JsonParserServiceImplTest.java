package com.example.testproject.service;

import com.example.testproject.dto.KeyValuePair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JsonParserServiceImplTest {

    private JsonParserService jsonParserService = new JsonParserServiceImpl();

    @Test
    void parseJsonObjectContainsDifferentDataTypes() {
        List<KeyValuePair> expected = new ArrayList<>();

        expected.add(new KeyValuePair("firstName", "John"));
        expected.add(new KeyValuePair("lastName", null));
        expected.add(new KeyValuePair("age", 25L));
        expected.add(new KeyValuePair("percent", 0.75));
        expected.add(new KeyValuePair("isActive", Boolean.TRUE));

        List<KeyValuePair> actual = jsonParserService.parseJsonFromFile(getFilePath("different_data_types.json"));

        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    void parseJsonContainsArrays() {
        List<KeyValuePair> expected = new ArrayList<>();

        expected.add(new KeyValuePair("user", "John"));
        expected.add(new KeyValuePair("city", "New York"));
        expected.add(new KeyValuePair("state", "NY"));
        expected.add(new KeyValuePair("type", "home"));
        expected.add(new KeyValuePair("number", "11-1-11"));
        expected.add(new KeyValuePair("type", "fax"));
        expected.add(new KeyValuePair("number", "22-2-22"));
        expected.add(new KeyValuePair("user", "Jake"));
        expected.add(new KeyValuePair("city", "Los Angeles"));
        expected.add(new KeyValuePair("state", "LA"));
        expected.add(new KeyValuePair("type", "home"));
        expected.add(new KeyValuePair("number", "33-3-33"));
        expected.add(new KeyValuePair("type", "fax"));
        expected.add(new KeyValuePair("number", "44-4-44"));

        List<KeyValuePair> actual = jsonParserService.parseJsonFromFile(getFilePath("json_contains_arrays.json"));

        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);
    }

    @Test
    void parseJsonContainsNestedObjects() {
        List<KeyValuePair> expected = new ArrayList<>();

        expected.add(new KeyValuePair("user", "John"));
        expected.add(new KeyValuePair("city", "New York"));
        expected.add(new KeyValuePair("name", "NY"));
        expected.add(new KeyValuePair("code", 12345L));

        List<KeyValuePair> actual = jsonParserService.parseJsonFromFile(getFilePath("contains_nested_objects.json"));

        assertThat(actual.size()).isEqualTo(expected.size());
        assertThat(actual).containsAll(expected);

    }

    @Test
    void parseJsonObjectIsEmpty() {
        List<KeyValuePair> actual = jsonParserService.parseJsonFromFile(getFilePath("empty.json"));
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(0);
    }

    @Test
    void checkExceptionThrownWhenFileCannotBeFound() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> jsonParserService.parseJsonFromFile("/home/non_existing_file.json"));
        assertThat(exception.getMessage()).isEqualTo("File read error");
    }

    @Test
    void checkExceptionThrownWhenJsonCannotBeParsed() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> jsonParserService.parseJsonFromFile(getFilePath("broken_json_structure.json")));
        assertThat(exception.getMessage()).isEqualTo("JSON cannot be parsed");
    }

    private String getFilePath(String fileName) {
        return this.getClass().getClassLoader().getResource(fileName).getPath();
    }

}