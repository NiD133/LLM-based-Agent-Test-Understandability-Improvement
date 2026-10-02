package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_parse_CharSequence_invalidLeapSecond {

    @Test
    public void factory_parse_CharSequence_invalidLeapSecond() {
        // A leap second (:60) is only valid at the end of a leap day.
        // "1972-11-11" is not a leap day, so parsing must be rejected.
        String leapSecondOnNonLeapDay = "1972-11-11T23:59:60Z";

        assertThrows(DateTimeException.class, () -> UtcInstant.parse(leapSecondOnNonLeapDay));
    }
}
