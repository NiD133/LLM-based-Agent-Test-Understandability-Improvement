package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianDate#getLong(java.time.temporal.TemporalField)} rejects
 * a time-based field that a date cannot supply.
 */
public class TestJulianChronology_test_getLong_unsupported {

    @Test
    public void getLong_withTimeBasedField_throwsUnsupportedTemporalTypeException() {
        JulianDate date = JulianDate.of(2012, 6, 30);

        // MINUTE_OF_DAY is a time field; a date has no time component to report.
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> date.getLong(MINUTE_OF_DAY));
    }
}
