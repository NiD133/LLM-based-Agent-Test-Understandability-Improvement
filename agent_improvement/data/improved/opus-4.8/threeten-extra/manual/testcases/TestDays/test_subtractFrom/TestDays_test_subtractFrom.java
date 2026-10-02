package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#subtractFrom(java.time.temporal.Temporal)}, which subtracts
 * the day amount from a given temporal (here a {@link LocalDate}).
 */
public class TestDays_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalDate startDate = LocalDate.of(2019, 1, 10);

        // Subtracting zero days leaves the date unchanged.
        assertEquals(LocalDate.of(2019, 1, 10), Days.of(0).subtractFrom(startDate));

        // Subtracting five days moves the date five days earlier.
        assertEquals(LocalDate.of(2019, 1, 5), Days.of(5).subtractFrom(startDate));
    }
}
