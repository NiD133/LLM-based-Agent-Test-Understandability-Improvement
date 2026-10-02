package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianDate#range(java.time.temporal.TemporalField)} rejects
 * a temporal field it does not support.
 */
public class TestJulianChronology_test_range_unsupported {

    @Test
    public void range_withUnsupportedField_throwsException() {
        JulianDate date = JulianDate.of(2012, 6, 30);

        // MINUTE_OF_DAY is a time field and is not supported by a date-only type.
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.range(MINUTE_OF_DAY));
    }
}
