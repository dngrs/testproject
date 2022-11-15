package com.example.testproject.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TypeConvertorUtilsTest {

    @ParameterizedTest
    @MethodSource("getTestData")
    void getTypedInputParameterTest(String input, Object expected) {
        Object object = TypeConvertorUtils.getTypedInputParameter(input);
        assertNotNull(object, "Object is null");
        assertThat(object).isEqualTo(expected);
    }

    @Test
    void checkObjectIsNullWhenInputStringEqualsNull() {
        Object object = TypeConvertorUtils.getTypedInputParameter("null");
        assertThat(object).isNull();
    }

    @Test
    void checkNullPointerExceptionThrownWhenInputIsNull() {
        assertThrows(NullPointerException.class, () -> TypeConvertorUtils.getTypedInputParameter(null));
    }

    private static Stream<Arguments> getTestData() {
        return Stream.of(
                Arguments.of("true", Boolean.valueOf("true")),
                Arguments.of("false", Boolean.valueOf("false")),
                Arguments.of("123456", Long.valueOf("123456")),
                Arguments.of("0.75", Double.valueOf("0.75")),
                Arguments.of("test_string", "test_string"));
    }

}