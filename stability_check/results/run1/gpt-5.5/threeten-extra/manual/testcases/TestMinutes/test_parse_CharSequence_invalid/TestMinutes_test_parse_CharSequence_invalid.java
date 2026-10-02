package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMinutes_test_parse_CharSequence_invalid {

    public static Object[][] data_invalid() {
        return new Object[][] {
                { "P3W" },
                { "P3Q" },
                { "P1M2Y" },
                { "3" },
                { "-3" },
                { "3M" },
                { "-3M" },
                { "P3M" },
                { "P3" },
                { "P-3" },
                { "PM" },
                { "T3" },
                { "P3M" },
                { "PT3S" },
                { "PT3" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(str));
    }
}
