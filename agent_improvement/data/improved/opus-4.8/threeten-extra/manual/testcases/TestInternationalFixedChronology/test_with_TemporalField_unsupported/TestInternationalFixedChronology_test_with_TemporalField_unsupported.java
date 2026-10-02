package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link InternationalFixedDate#with(java.time.temporal.TemporalField, long)}
 * rejects a time-based field that the date type does not support.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_with_TemporalField_unsupported {

    @Test
    public void test_with_TemporalField_unsupported() {
        InternationalFixedDate date = InternationalFixedDate.of(2012, 6, 28);

        // MINUTE_OF_DAY is a time field and has no meaning for a calendar date,
        // so adjusting it must raise UnsupportedTemporalTypeException.
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> date.with(MINUTE_OF_DAY, 0));
    }
}
