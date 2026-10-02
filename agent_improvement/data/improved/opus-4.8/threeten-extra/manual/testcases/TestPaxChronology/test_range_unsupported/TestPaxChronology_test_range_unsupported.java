package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link PaxDate#range(java.time.temporal.TemporalField)} rejects
 * fields that the Pax calendar does not support.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_range_unsupported {

    @Test
    public void range_throwsForUnsupportedField() {
        // MINUTE_OF_DAY is a time-based field and has no meaning for a date.
        PaxDate date = PaxDate.of(2012, 6, 28);

        assertThrows(UnsupportedTemporalTypeException.class, () -> date.range(MINUTE_OF_DAY));
    }
}
