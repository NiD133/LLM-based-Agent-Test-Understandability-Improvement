package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#from(java.time.temporal.TemporalAmount)} when converting from a {@link Duration}.
 */
public class TestWeeks_test_from_Duration {

    @Test
    public void from_duration_of_14_days_yields_2_weeks() {
        // 14 days is an exact multiple of 7, so it converts cleanly to 2 weeks.
        Weeks result = Weeks.from(Duration.ofDays(14));

        assertEquals(Weeks.of(2), result);
    }
}
