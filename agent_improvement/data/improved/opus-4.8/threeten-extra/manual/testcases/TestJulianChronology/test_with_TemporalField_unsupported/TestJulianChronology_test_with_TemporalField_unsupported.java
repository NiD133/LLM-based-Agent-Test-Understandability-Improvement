package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianDate#with(java.time.temporal.TemporalField, long)}
 * rejects a time-based field, which the date-only Julian calendar cannot support.
 */
public class TestJulianChronology_test_with_TemporalField_unsupported {

    @Test
    public void with_timeBasedField_throwsUnsupportedTemporalType() {
        JulianDate date = JulianDate.of(2012, 6, 30);

        // MINUTE_OF_DAY is a time field and has no meaning for a calendar date.
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.with(MINUTE_OF_DAY, 0));
    }
}
