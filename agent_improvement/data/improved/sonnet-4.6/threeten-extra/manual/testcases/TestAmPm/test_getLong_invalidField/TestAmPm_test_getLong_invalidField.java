package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_getLong_invalidField {

    /**
     * AmPm only supports AMPM_OF_DAY. Querying any other ChronoField
     * (e.g. MONTH_OF_YEAR) must throw UnsupportedTemporalTypeException.
     */
    @Test
    public void test_getLong_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.PM.getLong(MONTH_OF_YEAR));
    }
}
