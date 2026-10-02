package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#from(java.time.temporal.TemporalAmount)} rejects an
 * amount whose units cannot be converted to a whole number of days.
 */
public class TestDays_test_from_wrongUnit_remainder {

    @Test
    public void from_durationOfHours_notDivisibleIntoWholeDays_throws() {
        // 3 hours is a time-based amount that has no whole-day equivalent,
        // so the conversion leaves a remainder and must be rejected.
        Duration threeHours = Duration.ofHours(3);

        assertThrows(DateTimeException.class, () -> Days.from(threeHours));
    }
}
