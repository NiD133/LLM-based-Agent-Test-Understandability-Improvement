package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#plus(java.time.temporal.TemporalAmount)} rejects a
 * time-based amount.
 * <p>
 * A {@link Duration} measures time (here, hours), which cannot be converted to a
 * whole number of months, so adding it to a {@code Months} value must fail.
 */
public class TestMonths_test_plus_TemporalAmount_Duration {

    @Test
    public void plus_durationInHours_throwsDateTimeException() {
        Months oneMonth = Months.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneMonth.plus(twoHours));
    }
}
