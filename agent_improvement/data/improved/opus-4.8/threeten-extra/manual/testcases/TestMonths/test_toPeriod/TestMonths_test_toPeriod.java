package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#toPeriod()}.
 */
public class TestMonths_test_toPeriod {

    @Test
    public void toPeriod_returnsPeriodWithSameNumberOfMonths() {
        // Across a representative range of positive, zero and negative values,
        // Months.toPeriod() must yield a Period holding the identical month count.
        for (int monthCount = -20; monthCount < 20; monthCount++) {
            Period expected = Period.ofMonths(monthCount);
            Period actual = Months.of(monthCount).toPeriod();
            assertEquals(expected, actual);
        }
    }
}
