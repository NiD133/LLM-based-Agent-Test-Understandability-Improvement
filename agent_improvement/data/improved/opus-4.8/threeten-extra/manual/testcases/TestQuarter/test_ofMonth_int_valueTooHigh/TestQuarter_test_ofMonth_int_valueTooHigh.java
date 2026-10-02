package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#ofMonth(int)} rejects a month value above the
 * valid range of 1 to 12.
 */
public class TestQuarter_test_ofMonth_int_valueTooHigh {

    @Test
    public void ofMonth_throwsWhenMonthAboveMaximum() {
        // 13 is one past December (12), so no quarter exists for it.
        assertThrows(DateTimeException.class, () -> Quarter.ofMonth(13));
    }
}
