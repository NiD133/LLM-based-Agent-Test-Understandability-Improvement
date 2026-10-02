package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AmPm#getLong} rejects fields it does not support.
 */
public class TestAmPm_test_getLong_invalidField {

    /**
     * {@code AmPm} only supports the AMPM_OF_DAY field, so requesting an
     * unrelated ChronoField such as MONTH_OF_YEAR must fail rather than
     * return a value.
     */
    @Test
    public void getLong_withUnsupportedField_throwsUnsupportedTemporalTypeException() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> AmPm.PM.getLong(MONTH_OF_YEAR));
    }
}
