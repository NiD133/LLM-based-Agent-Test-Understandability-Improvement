package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#minus(java.time.temporal.TemporalAmount)} when the supplied
 * amount cannot be expressed as a whole number of months.
 */
public class TestMonths_test_minus_TemporalAmount_Duration {

    /**
     * Subtracting a time-based {@link Duration} (e.g. hours) from a {@code Months}
     * is not supported, because hours cannot be converted to whole months.
     * The operation must therefore fail with a {@link DateTimeException}.
     */
    @Test
    public void minus_durationInHours_throwsDateTimeException() {
        Months oneMonth = Months.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneMonth.minus(twoHours));
    }
}
