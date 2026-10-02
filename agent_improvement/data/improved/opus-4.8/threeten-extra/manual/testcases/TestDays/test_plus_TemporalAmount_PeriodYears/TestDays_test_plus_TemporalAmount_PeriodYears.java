package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#plus(java.time.temporal.TemporalAmount)} rejects a
 * temporal amount that cannot be expressed as a whole number of days.
 */
public class TestDays_test_plus_TemporalAmount_PeriodYears {

    @Test
    public void plus_withPeriodInYears_throwsBecauseYearsAreNotDays() {
        Days oneDay = Days.of(1);
        Period twoYears = Period.ofYears(2);

        // A year-based period has no fixed conversion to days, so plus(...) must reject it.
        assertThrows(DateTimeException.class, () -> oneDay.plus(twoYears));
    }
}
