package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_get_invalidField {

    @Test
    @DisplayName("get() throws UnsupportedTemporalTypeException for a field not supported by AmPm")
    public void test_get_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.PM.get(MONTH_OF_YEAR));
    }
}
