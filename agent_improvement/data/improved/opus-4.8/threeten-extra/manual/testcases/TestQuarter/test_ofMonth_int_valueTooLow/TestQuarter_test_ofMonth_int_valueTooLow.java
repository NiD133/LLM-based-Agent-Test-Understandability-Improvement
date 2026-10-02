package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#ofMonth(int)} rejects a month value that is below
 * the valid range of 1 to 12.
 */
public class TestQuarter_test_ofMonth_int_valueTooLow {

    /**
     * Month 0 is below the lowest valid month (January = 1), so
     * {@code ofMonth} must reject it with a {@link DateTimeException}.
     */
    @Test
    public void test_ofMonth_int_valueTooLow() {
        int monthBelowValidRange = 0;
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(monthBelowValidRange));
    }
}
