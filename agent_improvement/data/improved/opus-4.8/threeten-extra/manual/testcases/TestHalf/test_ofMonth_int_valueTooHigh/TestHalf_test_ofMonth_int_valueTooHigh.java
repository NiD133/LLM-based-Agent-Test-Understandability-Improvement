package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#ofMonth(int)} rejects a month value above the valid
 * range of 1 to 12.
 */
public class TestHalf_test_ofMonth_int_valueTooHigh {

    @Test
    public void ofMonth_rejectsMonthValueAboveMaximum() {
        int monthAboveMaximum = 13;

        assertThrows(DateTimeException.class, () -> Half.ofMonth(monthAboveMaximum));
    }
}
