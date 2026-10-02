package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#between(java.time.temporal.Temporal, java.time.temporal.Temporal)}.
 */
public class TestMonths_test_between {

    @Test
    public void test_between_returnsWholeMonthsBetweenTwoDates() {
        LocalDate startInclusive = LocalDate.of(2019, 1, 1);
        LocalDate endExclusive = LocalDate.of(2021, 1, 1);

        // Two whole years separate the dates, which equals 24 months.
        Months monthsBetween = Months.between(startInclusive, endExclusive);

        assertEquals(Months.of(24), monthsBetween);
    }
}
