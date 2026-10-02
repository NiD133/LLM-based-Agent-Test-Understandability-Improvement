package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_Duration {

    @Test
    public void test_plus_TemporalAmount_Duration() {
        assertThrows(DateTimeException.class, () -> Months.of(1).plus(Duration.ofHours(2)));
    }
}
