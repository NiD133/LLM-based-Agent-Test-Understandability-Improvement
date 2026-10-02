package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_Duration {

    @Test
    public void test_minus_TemporalAmount_Duration() {
        // Duration is time-based and cannot be converted to a whole number of years,
        // so Years.minus(Duration) must throw DateTimeException.
        assertThrows(DateTimeException.class, () -> Years.of(1).minus(Duration.ofHours(2)));
    }
}
