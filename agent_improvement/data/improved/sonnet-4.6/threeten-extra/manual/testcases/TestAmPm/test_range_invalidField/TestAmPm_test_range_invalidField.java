package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestAmPm_test_range_invalidField {

    @Test
    @DisplayName("range() throws UnsupportedTemporalTypeException for a field not supported by AmPm (e.g. MONTH_OF_YEAR)")
    public void test_range_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> AmPm.AM.range(MONTH_OF_YEAR));
    }
}
