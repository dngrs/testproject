package com.example.testproject;

import com.example.testproject.utils.MessageFormatter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class FieldCounterAppTest {

    @Test
    void checkApplicationOutput() {
        //Given
        String lineSeparator = System.getProperty("line.separator");
        String jsonFile = this.getClass().getClassLoader().getResource("different_data_types.json").getPath();
        String field = "firstName";
        String value = "John";

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream console = System.out;
        try {
            //When
            System.setOut(new PrintStream(bytes));
            FiledCounterApp.main(new String[]{jsonFile, field, value});

            //Then
            String[] messages = bytes.toString().split(lineSeparator);
            assertThat(messages.length).isEqualTo(2);
            assertThat(messages[0]).isEqualTo(MessageFormatter.getTotalCountMessage(1, field));
            assertThat(messages[1]).isEqualTo(MessageFormatter.getEqualCountMessage(1, field, value));
        } finally {
            System.setOut(console);
        }
    }

}
