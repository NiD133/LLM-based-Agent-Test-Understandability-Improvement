package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#subtractFrom(java.time.temporal.Temporal)}, which returns
 * the given temporal with this number of years subtracted.
 */
public class TestYears_test_subtractFrom {

    @Test
    public void test_subtractFrom() {
        LocalDate date = LocalDate.of(2019, 1, 10);

        // Subtracting zero years leaves the date unchanged.
        assertEquals(LocalDate.of(2019, 1, 10), Years.of(0).subtractFrom(date));

        // Subtracting five years moves the date back to the same day in 2014.
        assertEquals(LocalDate.of(2014, 1, 10), Years.of(5).subtractFrom(date));
    }
}
