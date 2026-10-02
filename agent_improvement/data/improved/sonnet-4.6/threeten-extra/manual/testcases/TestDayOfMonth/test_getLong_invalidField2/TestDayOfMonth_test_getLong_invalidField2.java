package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.IsoFields;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that DayOfMonth.getLong() throws UnsupportedTemporalTypeException
 * for non-ChronoField temporal fields that require more context than a
 * day-of-month can provide (e.g. DAY_OF_QUARTER needs month/year info).
 */
public class TestDayOfMonth_test_getLong_invalidField2 {

    private static final DayOfMonth TEST = DayOfMonth.of(12);

    @Test
    public void test_getLong_invalidField2() {
        // IsoFields.DAY_OF_QUARTER is not a ChronoField and requires month/year context
        // that DayOfMonth does not hold, so getLong must throw UnsupportedTemporalTypeException
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.getLong(IsoFields.DAY_OF_QUARTER));
    }
}
