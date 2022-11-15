package com.example.testproject.service;

import com.example.testproject.dto.KeyValuePair;
import com.example.testproject.utils.TypeConvertorUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonParserServiceImpl implements JsonParserService {

    public List<KeyValuePair> parseJsonFromFile(String pathToJsonFile) {
        String jsonString;
        try {
            jsonString = Files.readString(Path.of(pathToJsonFile));
        } catch (IOException ex) {
            throw new RuntimeException("File read error");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = mapper.readTree(jsonString);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("JSON cannot be parsed");
        }
        List<KeyValuePair> nodeList = new ArrayList<>();
        parseJson(nodeList, rootNode);
        return nodeList;
    }

    private void parseJson(List<KeyValuePair> keyPairList, JsonNode node) {
        if (node.isContainerNode()) {
            if (node.isArray()) {
                Iterator<JsonNode> elements = node.elements();
                while (elements.hasNext()) {
                    JsonNode nodeCurrent = elements.next();
                    Iterator<Map.Entry<String, JsonNode>> fieldsIterator = nodeCurrent.fields();
                    while (fieldsIterator.hasNext()) {
                        Map.Entry<String, JsonNode> field = fieldsIterator.next();
                        String key = field.getKey();
                        JsonNode value = field.getValue();
                        if (value.isValueNode()) {
                            keyPairList.add(new KeyValuePair(key, convertJsonValueNodeToObject(value)));
                        } else {
                            parseJson(keyPairList, value);
                        }
                    }
                }
            } else if (node.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();
                while (fieldsIterator.hasNext()) {
                    Map.Entry<String, JsonNode> field = fieldsIterator.next();
                    String key = field.getKey();
                    JsonNode value = field.getValue();
                    if (value.isValueNode()) {
                        keyPairList.add(new KeyValuePair(key, convertJsonValueNodeToObject(value)));
                    } else {
                        parseJson(keyPairList, value);
                    }
                }
            }
        }
    }

    private Object convertJsonValueNodeToObject(JsonNode jsonNode) {
        JsonNodeType type = jsonNode.getNodeType();
        Object o = null;
        switch (type) {
            case STRING:
                o = jsonNode.asText();
                break;
            case BOOLEAN:
                o = jsonNode.asBoolean();
                break;
            case NUMBER:
                if (TypeConvertorUtils.isNumber(jsonNode.asText())) {
                    o = jsonNode.asLong();
                } else {
                    o = jsonNode.asDouble();
                }
                break;
            case NULL:
                break;
        }
        return o;
    }

}
