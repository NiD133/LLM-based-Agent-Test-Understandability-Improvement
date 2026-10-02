package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * rejects a time-based unit that a date cannot measure.
 */
public class TestBritishCutoverChronology_test_until_TemporalUnit_unsupported {

    @Test
    public void until_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        BritishCutoverDate start = BritishCutoverDate.of(2012, 6, 30);
        BritishCutoverDate end = BritishCutoverDate.of(2012, 7, 1);

        // MINUTES is a time-based unit, so it cannot be used to measure the span between two dates.
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> start.until(end, MINUTES));
    }
}
