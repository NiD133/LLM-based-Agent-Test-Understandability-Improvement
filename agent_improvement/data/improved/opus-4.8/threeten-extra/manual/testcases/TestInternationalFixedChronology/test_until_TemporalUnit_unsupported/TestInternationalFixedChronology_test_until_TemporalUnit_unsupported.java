package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * rejects time-based units that the date cannot measure.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_until_TemporalUnit_unsupported {

    @Test
    public void until_withTimeBasedUnit_throwsUnsupportedTemporalTypeException() {
        InternationalFixedDate start = InternationalFixedDate.of(2012, 6, 28);
        InternationalFixedDate end = InternationalFixedDate.of(2012, 7, 1);

        // MINUTES is a time-based unit and is not supported between two dates.
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
