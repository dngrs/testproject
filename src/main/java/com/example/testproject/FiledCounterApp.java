package com.example.testproject;

import com.example.testproject.dto.Counter;
import com.example.testproject.service.*;

import static com.example.testproject.utils.MessageFormatter.getEqualCountMessage;
import static com.example.testproject.utils.MessageFormatter.getTotalCountMessage;
import static java.util.Objects.requireNonNull;

public class FiledCounterApp {

    public static void main(String[] args) {
        String pathToJsonFile = requireNonNull(args[0]);
        String field = requireNonNull(args[1]);
        String value = requireNonNull(args[2]);

        JsonParserService jsonParserService = new JsonParserServiceImpl();
        PropertyCounterService propertyCounterService = new PropertyCounterServiceImpl(jsonParserService);

        Counter counter = propertyCounterService.countField(pathToJsonFile, field, value);
        System.out.println(getTotalCountMessage(counter.getTotal(), field));
        System.out.println(getEqualCountMessage(counter.getEqualsSpecificValue(), field, value));
    }

}
