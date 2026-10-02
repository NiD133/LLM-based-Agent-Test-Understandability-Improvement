package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#getLong} rejects fields it does not support.
 */
public class TestQuarter_test_getLong_invalidField {

    /**
     * A quarter only supports the QUARTER_OF_YEAR field. Asking it for an
     * unsupported ChronoField such as MONTH_OF_YEAR must fail.
     */
    @Test
    public void getLong_withUnsupportedField_throwsUnsupportedTemporalTypeException() {
        assertThrows(UnsupportedTemporalTypeException.class,
                () -> Quarter.Q2.getLong(MONTH_OF_YEAR));
    }
}
