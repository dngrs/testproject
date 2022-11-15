package com.example.testproject.service;

import com.example.testproject.dto.Counter;
import com.example.testproject.dto.KeyValuePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PropertyCounterServiceImplTest {

    @InjectMocks
    private PropertyCounterServiceImpl propertyCounterService;

    @Mock
    private JsonParserService jsonParserService;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    void checkCountersWhenExpectedFieldNotFound() {
        //Given
        String path = "/test/file.json";
        String field = "percent";
        String value = "100.5";

        List<KeyValuePair> list = new ArrayList<>();
        list.add(new KeyValuePair("userName", "name1"));
        list.add(new KeyValuePair("age", 10L));
        list.add(new KeyValuePair("isActive", Boolean.TRUE));
        list.add(new KeyValuePair("amount", 100.5));

        Mockito.when(jsonParserService.parseJsonFromFile(stringArgumentCaptor.capture())).thenReturn(list);

        //When
        Counter counter = propertyCounterService.countField(path, field, value);

        //Then
        Mockito.verify(jsonParserService, times(1)).parseJsonFromFile(anyString());
        assertThat(stringArgumentCaptor.getValue()).isEqualTo(path);

        assertThat(counter).isNotNull();
        assertThat(counter.getTotal()).isEqualTo(0);
        assertThat(counter.getEqualsSpecificValue()).isEqualTo(0);
    }

    @Test
    void checkCountersWhenFoundExpectedFieldAndValue() {
        //Given
        String path = "/test";
        String field = "userName";
        String value = "name1";

        List<KeyValuePair> list = new ArrayList<>();
        list.add(new KeyValuePair("userName", "name1"));
        list.add(new KeyValuePair("userName", "name2"));
        list.add(new KeyValuePair("userLastName", "name1"));
        list.add(new KeyValuePair("userLastName", "lastName2"));

        Mockito.when(jsonParserService.parseJsonFromFile(anyString())).thenReturn(list);

        //When
        Counter counter = propertyCounterService.countField(path, field, value);

        //Then
        assertThat(counter.getTotal()).isEqualTo(2);
        assertThat(counter.getEqualsSpecificValue()).isEqualTo(1);
    }

    @Test
    void checkCountersWhenFoundExpectedFieldAndValueNotFound() {
        //Given
        String path = "/test";
        String field = "age";
        String value = "30";

        List<KeyValuePair> list = new ArrayList<>();
        list.add(new KeyValuePair("firstName", "name"));
        list.add(new KeyValuePair("age", 20L));
        list.add(new KeyValuePair("isActive", Boolean.TRUE));
        list.add(new KeyValuePair("isActive", Boolean.TRUE));

        Mockito.when(jsonParserService.parseJsonFromFile(anyString())).thenReturn(list);

        //When
        Counter counter = propertyCounterService.countField(path, field, value);

        //Then
        assertThat(counter.getTotal()).isEqualTo(1);
        assertThat(counter.getEqualsSpecificValue()).isEqualTo(0);
    }

    @Test
    void checkCountersWhenFoundExpectedFieldAndValueEqualsToNull() {
        //Given
        String path = "/test";
        String field = "lastName";
        String value = "null";

        List<KeyValuePair> list = new ArrayList<>();
        list.add(new KeyValuePair("firstName", "name"));
        list.add(new KeyValuePair("lastName", null));

        Mockito.when(jsonParserService.parseJsonFromFile(anyString())).thenReturn(list);

        //When
        Counter counter = propertyCounterService.countField(path, field, value);

        //Then
        assertThat(counter.getTotal()).isEqualTo(1);
        assertThat(counter.getEqualsSpecificValue()).isEqualTo(1);
    }

}