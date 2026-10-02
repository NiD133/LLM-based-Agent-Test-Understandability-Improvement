package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ofHours_overflow {

    // Minutes.ofHours multiplies the input by 60 (MINUTES_PER_HOUR).
    // Any hours value above Integer.MAX_VALUE / 60 will overflow when converted to minutes.
    private static final int OVERFLOW_HOURS = (Integer.MAX_VALUE / 60) + 60;

    @Test
    public void test_ofHours_overflow() {
        assertThrows(ArithmeticException.class, () -> Minutes.ofHours(OVERFLOW_HOURS));
    }
}
