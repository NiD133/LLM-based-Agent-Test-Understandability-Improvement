package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#ofMonth(int)} rejects a month-of-year that is below
 * the valid range of 1 to 12.
 */
public class TestHalf_test_ofMonth_int_valueTooLow {

    private static final int MONTH_BELOW_VALID_RANGE = 0;

    @Test
    public void ofMonth_throwsWhenMonthIsTooLow() {
        assertThrows(DateTimeException.class, () -> Half.ofMonth(MONTH_BELOW_VALID_RANGE));
    }
}
