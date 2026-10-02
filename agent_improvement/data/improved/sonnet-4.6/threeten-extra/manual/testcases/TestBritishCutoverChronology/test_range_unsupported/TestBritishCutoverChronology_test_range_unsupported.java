package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link BritishCutoverDate#range} throws when given a time-based field.
 * {@code MINUTE_OF_DAY} is not supported on a date-only object, so querying its
 * range must raise {@link UnsupportedTemporalTypeException}.
 */
public class TestBritishCutoverChronology_test_range_unsupported {

    @Test
    public void test_range_unsupported() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> BritishCutoverDate.of(2012, 6, 30).range(MINUTE_OF_DAY));
    }
}
