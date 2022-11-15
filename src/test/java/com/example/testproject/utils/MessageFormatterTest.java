package com.example.testproject.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageFormatterTest {

    @Test
    void getTotalCountMessageTest() {
        assertThat(MessageFormatter.getTotalCountMessage(1, "testField"))
                .isEqualTo("found 1 objects with field \"testField\"");
    }

    @Test
    void getEqualCountMessageTest() {
        assertThat(MessageFormatter.getEqualCountMessage(1, "testField", "testValue"))
                .isEqualTo("found 1 objects where \"testField\" equals \"testValue\"");
    }

}