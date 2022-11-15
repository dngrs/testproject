package com.example.testproject.utils;

public class TypeConvertorUtils {

    private TypeConvertorUtils() {
    }

    public static Object getTypedInputParameter(String input) {
        if ("null".equals(input)) {
            return null;
        } else if ("true".equals(input) || "false".equals(input)) {
            return Boolean.valueOf(input);
        } else if (isNumber(input)) {
            return Long.valueOf(input);
        } else if (isFloatingPointNumber(input)) {
            return Double.valueOf(input);
        } else {
            return input;
        }
    }

    public static boolean isNumber(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isFloatingPointNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
