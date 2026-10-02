package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_Duration {

    @Test
    public void test_minus_TemporalAmount_Duration() {
        // Duration is time-based; Weeks only supports week-based amounts,
        // so subtracting a Duration must throw DateTimeException.
        assertThrows(DateTimeException.class, () -> Weeks.of(1).minus(Duration.ofHours(2)));
    }
}
