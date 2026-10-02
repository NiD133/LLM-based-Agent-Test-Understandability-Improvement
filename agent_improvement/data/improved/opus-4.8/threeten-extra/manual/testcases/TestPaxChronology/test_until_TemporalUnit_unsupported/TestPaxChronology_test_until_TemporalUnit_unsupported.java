package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link PaxDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * rejects temporal units that the Pax calendar does not support.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_TemporalUnit_unsupported {

    @Test
    public void until_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() {
        PaxDate start = PaxDate.of(2012, 6, 28);
        PaxDate end = PaxDate.of(2012, 7, 1);

        // MINUTES is a time-based unit and is not valid for a date-only PaxDate.
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
