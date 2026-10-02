package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Weeks#minus(java.time.temporal.TemporalAmount)} rejects a
 * {@link Period} expressed in months.
 */
public class TestWeeks_test_minus_TemporalAmount_PeriodMonths {

    /**
     * A period of months cannot be converted to a whole number of weeks, so
     * subtracting it from a {@code Weeks} value must fail with a
     * {@link DateTimeException}.
     */
    @Test
    public void minus_periodOfMonths_throwsDateTimeException() {
        Weeks oneWeek = Weeks.of(1);
        Period twoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> oneWeek.minus(twoMonths));
    }
}
