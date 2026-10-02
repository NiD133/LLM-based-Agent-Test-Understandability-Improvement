package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestMinutes_test_parse_CharSequence_invalid {

    /**
     * Strings that Minutes.parse() must reject with DateTimeParseException.
     *
     * Groups:
     *  - week / unknown designator (P3W, P3Q)
     *  - wrong unit order (P1M2Y)
     *  - missing 'P' prefix (3, -3, 3M, -3M)
     *  - month-based period, not time-based (P3M)
     *  - incomplete period (P3, P-3, PM)
     *  - missing 'P' before 'T' (T3)
     *  - seconds not supported (PT3S)
     *  - trailing digits without unit (PT3)
     */
    @ParameterizedTest
    @ValueSource(strings = {
        // unsupported or unknown designators
        "P3W",
        "P3Q",
        // wrong unit order
        "P1M2Y",
        // bare numbers – missing ISO period prefix
        "3",
        "-3",
        "3M",
        "-3M",
        // month-based period (not convertible to minutes without a reference date)
        "P3M",
        // incomplete: no value after 'P'
        "P3",
        "P-3",
        // missing numeric value before 'M'
        "PM",
        // missing 'P' before time section
        "T3",
        // duplicate (same rule as P3M above – month designator)
        "P3M",
        // unsupported seconds unit
        "PT3S",
        // digits without a trailing unit letter
        "PT3"
    })
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Minutes.parse(str));
    }
}
