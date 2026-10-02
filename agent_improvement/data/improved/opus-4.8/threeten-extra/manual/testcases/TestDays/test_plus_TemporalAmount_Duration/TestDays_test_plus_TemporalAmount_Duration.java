package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#plus(java.time.temporal.TemporalAmount)} rejects a
 * {@link Duration}.
 * <p>
 * A {@code Days} amount only supports the {@code DAYS} unit. A {@code Duration}
 * is measured in seconds/nanoseconds, which cannot be converted to a whole
 * number of days, so adding one must fail with a {@link DateTimeException}.
 */
public class TestDays_test_plus_TemporalAmount_Duration {

    @Test
    public void plus_durationInHours_throwsDateTimeException() {
        Days oneDay = Days.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneDay.plus(twoHours));
    }
}
