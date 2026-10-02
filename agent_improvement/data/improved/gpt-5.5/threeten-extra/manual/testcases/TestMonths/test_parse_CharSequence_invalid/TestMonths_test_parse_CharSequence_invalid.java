package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMonths_test_parse_CharSequence_invalid {

    public static Object[][] data_invalid() {
        return new Object[][] {
                { "P3W" },
                { "P3D" },
                { "P3Q" },
                { "P1M2Y" },
                { "3" },
                { "-3" },
                { "3M" },
                { "-3M" },
                { "P3" },
                { "P-3" },
                { "PM" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String text) {
        assertThrows(DateTimeParseException.class, () -> Months.parse(text));
    }
}
