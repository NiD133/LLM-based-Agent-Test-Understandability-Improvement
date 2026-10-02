package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Days#minus(java.time.temporal.TemporalAmount)} when given a
 * {@link Duration} amount.
 * <p>
 * {@code Days} only supports the DAYS unit. A {@code Duration} measured in hours
 * cannot be converted to a whole number of days, so subtracting it must fail
 * rather than silently truncating.
 */
public class TestDays_test_minus_TemporalAmount_Duration {

    @Test
    public void minus_durationInHours_throwsDateTimeException() {
        Days oneDay = Days.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneDay.minus(twoHours));
    }
}
