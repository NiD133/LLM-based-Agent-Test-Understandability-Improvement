package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#minus(java.time.temporal.TemporalAmount)} rejects a
 * {@link Period} expressed in years, because such a period cannot be converted
 * to a whole number of days.
 */
public class TestDays_test_minus_TemporalAmount_PeriodYears {

    @Test
    public void minus_periodInYears_throwsDateTimeException() {
        Days oneDay = Days.of(1);
        Period twoYears = Period.ofYears(2);

        assertThrows(DateTimeException.class, () -> oneDay.minus(twoYears));
    }
}
