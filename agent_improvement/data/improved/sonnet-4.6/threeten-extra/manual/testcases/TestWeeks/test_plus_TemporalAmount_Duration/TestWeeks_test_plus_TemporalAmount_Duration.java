package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_TemporalAmount_Duration {

    // Duration uses time-based units (e.g. HOURS) that cannot be converted to weeks,
    // so Weeks.plus(Duration) must throw DateTimeException.
    @Test
    public void test_plus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Weeks.of(1).plus(Duration.ofHours(2)));
    }
}
