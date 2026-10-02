package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_Duration {

    /**
     * Duration is a time-based amount (hours, seconds, nanos) and cannot be
     * converted to a whole number of months. Months.plus(TemporalAmount) delegates
     * to Months.from(), which must throw DateTimeException for such incompatible units.
     */
    @Test
    public void test_plus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Months.of(1).plus(Duration.ofHours(2)));
    }
}
