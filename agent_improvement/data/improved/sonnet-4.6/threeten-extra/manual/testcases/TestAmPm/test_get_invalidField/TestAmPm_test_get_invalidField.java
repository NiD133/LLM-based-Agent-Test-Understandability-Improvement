package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_get_invalidField {

    /**
     * AmPm only supports the AMPM_OF_DAY field; querying any other ChronoField
     * must throw UnsupportedTemporalTypeException.
     */
    @Test
    public void test_get_invalidField() {
        // MONTH_OF_YEAR is not a field that AmPm can provide, so get() must reject it.
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.PM.get(MONTH_OF_YEAR));
    }
}
