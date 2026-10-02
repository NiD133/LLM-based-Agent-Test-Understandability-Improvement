package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_Duration {

    // Duration is time-based (seconds/nanos) and cannot be converted to months,
    // so Months.minus(Duration) must throw DateTimeException.
    @Test
    public void test_minus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Months.of(1).minus(Duration.ofHours(2)));
    }
}
