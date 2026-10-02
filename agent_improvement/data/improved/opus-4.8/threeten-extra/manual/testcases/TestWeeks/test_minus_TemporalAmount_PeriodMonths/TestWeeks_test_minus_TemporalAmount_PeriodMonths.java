package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Weeks#minus(java.time.temporal.TemporalAmount)} rejects a
 * {@code Period} expressed in months.
 * <p>
 * Subtraction first converts the amount to whole weeks via {@code Weeks.from}.
 * Months cannot be converted to a whole number of weeks, so the operation must
 * fail with a {@link DateTimeException} rather than silently producing a result.
 */
public class TestWeeks_test_minus_TemporalAmount_PeriodMonths {

    @Test
    public void minus_periodInMonths_throwsBecauseMonthsAreNotWholeWeeks() {
        Weeks oneWeek = Weeks.of(1);
        Period twoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> oneWeek.minus(twoMonths));
    }
}
