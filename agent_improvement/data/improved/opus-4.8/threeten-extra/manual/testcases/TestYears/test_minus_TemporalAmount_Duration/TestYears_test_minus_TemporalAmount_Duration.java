package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#minus(java.time.temporal.TemporalAmount)} rejects a
 * time-based amount such as a {@link Duration}, which cannot be converted to a
 * whole number of years.
 */
public class TestYears_test_minus_TemporalAmount_Duration {

    @Test
    public void minus_durationInHours_throwsDateTimeException() {
        Years oneYear = Years.of(1);
        Duration twoHours = Duration.ofHours(2);

        assertThrows(DateTimeException.class, () -> oneYear.minus(twoHours));
    }
}
