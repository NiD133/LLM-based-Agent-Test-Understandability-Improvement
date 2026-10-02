package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSeconds_test_parse_CharSequence_invalid {

    /**
     * Invalid ISO-8601 duration strings that Seconds.parse() must reject.
     * Each entry is a string that does not conform to the accepted format
     * (PnDTnHnMnS variants) and should trigger a DateTimeParseException.
     */
    public static Object[][] data_invalid() {
        return new Object[][] {
            // Week-based durations are not supported
            { "P3W" },
            // Unknown unit designators
            { "P3Q" },
            // Wrong ordering of components (year after month)
            { "P1M2Y" },
            // Missing ISO duration prefix 'P'
            { "3" },
            { "-3" },
            { "3S" },
            { "-3S" },
            // 'P' present but missing required time designator 'T' before seconds
            { "P3S" },
            // 'P' present but no unit designator at all
            { "P3" },
            { "P-3" },
            { "PS" },
            // Missing leading 'P'
            { "T3" },
            // Time section present but no numeric value for seconds
            { "PT3" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_invalid")
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Seconds.parse(str));
    }
}
