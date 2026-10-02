package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#subtractFrom(java.time.temporal.Temporal)}, which subtracts
 * the held number of weeks from a given temporal (here, a {@link LocalDate}).
 */
public class TestWeeks_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        // Subtracting zero weeks leaves the date unchanged.
        assertEquals(startDate, Weeks.of(0).subtractFrom(startDate));

        // Subtracting 5 weeks (35 days) moves the date back from 2019-01-10 to 2018-12-06.
        assertEquals(LocalDate.of(2018, 12, 6), Weeks.of(5).subtractFrom(startDate));
    }
}
