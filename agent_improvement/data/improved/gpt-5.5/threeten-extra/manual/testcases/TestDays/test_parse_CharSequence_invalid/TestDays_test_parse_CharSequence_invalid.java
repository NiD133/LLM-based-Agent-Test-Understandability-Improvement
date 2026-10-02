package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDays_test_parse_CharSequence_invalid {

    public static Object[][] data_invalid() {
        return new Object[][] {
                {"P3Y"},
                {"P3M"},
                {"P3Q"},
                {"P1D2W"},
                {"3"},
                {"-3"},
                {"3D"},
                {"-3D"},
                {"P3"},
                {"P-3"},
                {"P"},
                {"PD"},
                {"PW"},
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String invalidText) {
        assertThrows(DateTimeParseException.class, () -> Days.parse(invalidText));
    }
}
