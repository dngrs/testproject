package com.example.testproject.utils;

public class MessageFormatter {

    private MessageFormatter() {
    }

    public static String getTotalCountMessage(long totalCounter, String field) {
        String totalCountMessage = "found %s objects with field \"%s\"";
        return String.format(totalCountMessage, totalCounter, field);
    }

    public static String getEqualCountMessage(long equalCounter, String field, String value) {
        String equalCountMessage = "found %s objects where \"%s\" equals \"%s\"";
        return String.format(equalCountMessage, equalCounter, field, value);
    }

}
