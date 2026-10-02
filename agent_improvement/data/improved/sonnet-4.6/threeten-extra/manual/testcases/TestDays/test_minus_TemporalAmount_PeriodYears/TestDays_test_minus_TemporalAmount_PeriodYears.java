package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_PeriodYears {

    // Days.minus() delegates to Days.from(), which rejects year-based units
    // because years cannot be converted to a fixed number of days.
    @Test
    public void test_minus_TemporalAmount_PeriodYears() {
        assertThrows(DateTimeException.class, () -> Days.of(1).minus(Period.ofYears(2)));
    }
}
