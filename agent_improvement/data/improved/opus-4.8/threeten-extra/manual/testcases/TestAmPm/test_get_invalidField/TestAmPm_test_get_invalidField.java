package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link AmPm#get(java.time.temporal.TemporalField)} behaviour when
 * queried with a field that an am-pm value does not support.
 */
public class TestAmPm_test_get_invalidField {

    /**
     * An am-pm only supports the AMPM_OF_DAY field, so requesting an unrelated
     * calendar field such as MONTH_OF_YEAR must be rejected.
     */
    @Test
    public void get_withUnsupportedField_throwsUnsupportedTemporalTypeException() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> AmPm.PM.get(MONTH_OF_YEAR));
    }
}
