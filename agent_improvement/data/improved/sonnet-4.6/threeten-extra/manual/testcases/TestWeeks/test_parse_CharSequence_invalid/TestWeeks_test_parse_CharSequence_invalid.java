package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestWeeks_test_parse_CharSequence_invalid {

    @ParameterizedTest
    @ValueSource(strings = {
        // Wrong temporal unit designator (not 'W')
        "P3Y", "P3M", "P3D",
        // Missing 'P' ISO-8601 prefix
        "3", "-3", "3Y", "-3Y",
        // Missing week count or 'W' suffix
        "P3", "P-3", "PY"
    })
    public void test_parse_CharSequence_invalid(String str) {
        assertThrows(DateTimeParseException.class, () -> Weeks.parse(str));
    }
}
