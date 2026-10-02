package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#minus(java.time.temporal.TemporalAmount)} rejects a
 * temporal amount whose units cannot be converted to whole months.
 */
public class TestMonths_test_minus_TemporalAmount_PeriodDays {

    @Test
    public void minus_periodInDays_throwsBecauseDaysCannotConvertToMonths() {
        // A Period measured in days has no whole-month equivalent, so subtracting
        // it from a Months amount must fail.
        Period twoDays = Period.ofDays(2);

        assertThrows(DateTimeException.class, () -> Months.of(1).minus(twoDays));
    }
}
