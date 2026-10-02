package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_Duration {

    // Days.plus(TemporalAmount) delegates to Days.from(), which only accepts day-based units.
    // Duration is time-based (hours, minutes, seconds) and cannot be converted to a whole number
    // of days, so a DateTimeException is expected.
    @Test
    public void test_plus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Days.of(1).plus(Duration.ofHours(2)));
    }
}
