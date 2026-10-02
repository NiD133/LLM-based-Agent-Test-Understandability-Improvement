package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_Duration {

    @Test
    public void test_minus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Days.of(1).minus(Duration.ofHours(2)));
    }
}
