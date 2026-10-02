package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DayOfYear#get(java.time.temporal.TemporalField)} rejects a field
 * it does not support.
 */
public class TestDayOfYear_test_get_invalidField {

    /** Sample day-of-year used as the subject under test. */
    private static final DayOfYear DAY_OF_YEAR = DayOfYear.of(12);

    @Test
    public void get_withUnsupportedField_throwsUnsupportedTemporalType() {
        // DayOfYear only supports DAY_OF_YEAR; MONTH_OF_YEAR must be rejected.
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DAY_OF_YEAR.get(MONTH_OF_YEAR));
    }
}
