package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * rejects a time-based unit, since a Julian date has no time component.
 */
public class TestJulianChronology_test_until_TemporalUnit_unsupported {

    @Test
    public void until_withTimeBasedUnit_throwsUnsupportedTemporalType() {
        JulianDate start = JulianDate.of(2012, 6, 30);
        JulianDate end = JulianDate.of(2012, 7, 1);

        // MINUTES is a time-based unit and is not supported by a date-only type.
        assertThrows(UnsupportedTemporalTypeException.class, () -> start.until(end, MINUTES));
    }
}
