package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestYears_test_plus_TemporalAmount_Duration {

    @Test
    public void test_plus_TemporalAmount_Duration() {
        // Duration is a time-based amount; Years only supports year-based units,
        // so adding a Duration must throw DateTimeException.
        assertThrows(DateTimeException.class, () -> Years.of(1).plus(Duration.ofHours(2)));
    }
}
