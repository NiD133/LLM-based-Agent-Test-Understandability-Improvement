package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#plus(java.time.temporal.TemporalAmount)} rejects a
 * temporal amount whose units cannot be converted to a whole number of months.
 */
public class TestMonths_test_plus_TemporalAmount_PeriodDays {

    @Test
    public void plus_periodInDays_throwsBecauseDaysCannotConvertToMonths() {
        // A Period measured in days has no whole-month equivalent, so adding it
        // to a Months amount must fail rather than silently dropping the days.
        Period twoDays = Period.ofDays(2);

        assertThrows(DateTimeException.class, () -> Months.of(1).plus(twoDays));
    }
}
