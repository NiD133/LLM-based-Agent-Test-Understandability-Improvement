package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestMonths_test_parse_CharSequence_invalid {

    public static Object[][] data_invalid() {
        return new Object[][] {
            { "P3W" },   // weeks unit is not supported
            { "P3D" },   // days unit is not supported
            { "P3Q" },   // unknown unit suffix
            { "P1M2Y" }, // units out of order (M before Y)
            { "3" },     // missing 'P' prefix and unit suffix
            { "-3" },    // missing 'P' prefix and unit suffix
            { "3M" },    // missing 'P' prefix
            { "-3M" },   // missing 'P' prefix
            { "P3" },    // missing unit suffix
            { "P-3" },   // missing unit suffix after sign
            { "PM" },    // missing numeric value
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Months.parse(str));
    }
}
