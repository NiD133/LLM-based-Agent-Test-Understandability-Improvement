package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AmPm#get(java.time.temporal.TemporalField)} rejects a
 * field that an am-pm value cannot supply.
 */
public class TestAmPm_test_get_invalidField {

    /**
     * An am-pm only supports the AMPM_OF_DAY field, so requesting an unrelated
     * ChronoField such as MONTH_OF_YEAR must fail with an
     * UnsupportedTemporalTypeException.
     */
    @Test
    public void test_get_invalidField() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> AmPm.PM.get(MONTH_OF_YEAR));
    }
}
