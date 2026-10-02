package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_range_invalidField {

    @Test
    public void test_range_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.AM.range(MONTH_OF_YEAR));
    }
}
