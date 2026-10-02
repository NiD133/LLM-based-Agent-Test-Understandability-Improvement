package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedDate#range(java.time.temporal.TemporalField)}
 * rejects a field that the International Fixed calendar does not support.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_range_unsupported {

    @Test
    public void range_withUnsupportedField_throws() {
        // MINUTE_OF_DAY is a time field and is not supported by a date-only type.
        InternationalFixedDate date = InternationalFixedDate.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.range(MINUTE_OF_DAY));
    }
}
