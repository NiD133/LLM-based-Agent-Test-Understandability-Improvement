package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestYears_test_parse_CharSequence_invalid {

    public static Object[][] data_invalid() {
        return new Object[][] {
                { "P3M" },
                { "P3W" },
                { "P3D" },
                { "3" },
                { "-3" },
                { "3Y" },
                { "-3Y" },
                { "P3" },
                { "P-3" },
                { "PY" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Years.parse(str));
    }
}
