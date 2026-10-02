package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#plus(java.time.temporal.TemporalAmount)} when the amount
 * is a time-based {@link Duration}.
 */
public class TestWeeks_test_plus_TemporalAmount_Duration {

    /**
     * A {@code Duration} measures time in hours/seconds, which cannot be
     * converted to a whole number of weeks. Adding one must therefore fail.
     */
    @Test
    public void plus_durationInHours_throwsDateTimeException() {
        Weeks oneWeek = Weeks.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneWeek.plus(twoHours));
    }
}
