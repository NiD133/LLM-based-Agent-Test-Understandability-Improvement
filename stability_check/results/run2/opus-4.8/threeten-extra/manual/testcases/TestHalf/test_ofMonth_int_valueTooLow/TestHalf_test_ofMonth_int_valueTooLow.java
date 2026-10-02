package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#ofMonth(int)} rejects a month-of-year value that is
 * below the valid range of 1 to 12.
 */
public class TestHalf_test_ofMonth_int_valueTooLow {

    @Test
    public void ofMonth_throwsWhenMonthBelowValidRange() {
        // 0 is one below the lowest valid month-of-year (1 = January),
        // so ofMonth must reject it with a DateTimeException.
        assertThrows(DateTimeException.class, () -> Half.ofMonth(0));
    }
}
