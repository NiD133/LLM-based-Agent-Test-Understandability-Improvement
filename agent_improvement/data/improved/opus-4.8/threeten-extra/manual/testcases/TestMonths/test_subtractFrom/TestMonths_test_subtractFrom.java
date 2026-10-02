package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#subtractFrom(java.time.temporal.Temporal)}.
 * <p>
 * {@code subtractFrom} moves a date backwards by the number of months held by
 * the {@code Months} amount, leaving the date unchanged when the amount is zero.
 */
public class TestMonths_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        // Subtracting zero months leaves the date unchanged.
        assertEquals(startDate, Months.of(0).subtractFrom(startDate));

        // Subtracting 5 months from 2019-01-10 goes back to 2018-08-10.
        assertEquals(LocalDate.of(2018, 8, 10), Months.of(5).subtractFrom(startDate));
    }
}
