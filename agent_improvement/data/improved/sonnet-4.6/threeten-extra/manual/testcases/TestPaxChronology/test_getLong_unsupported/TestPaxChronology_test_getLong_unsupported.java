package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that PaxDate.getLong() throws UnsupportedTemporalTypeException
 * for temporal fields that are not supported by a date-only type.
 */
@SuppressWarnings("static-method")
public class TestPaxChronology_test_getLong_unsupported {

    @Test
    public void test_getLong_unsupported() {
        // MINUTE_OF_DAY is a time-based field; a date-only PaxDate must reject it
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> PaxDate.of(2012, 6, 28).getLong(MINUTE_OF_DAY));
    }
}
