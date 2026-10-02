package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#get(java.time.temporal.TemporalUnit)}.
 */
public class TestWeeks_test_get {

    @Test
    public void get_withWeeksUnit_returnsWeekCount() {
        Weeks sixWeeks = Weeks.of(6);

        long amount = sixWeeks.get(ChronoUnit.WEEKS);

        assertEquals(6, amount);
    }
}
