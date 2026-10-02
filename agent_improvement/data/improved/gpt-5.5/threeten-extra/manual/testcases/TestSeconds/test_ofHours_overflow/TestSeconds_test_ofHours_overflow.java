package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofHours_overflow {

    private static final int SECONDS_PER_HOUR = 3600;

    @Test
    public void test_ofHours_overflow() {
        int hoursThatOverflowWhenConvertedToSeconds = (Integer.MAX_VALUE / SECONDS_PER_HOUR) + SECONDS_PER_HOUR;

        assertThrows(
                ArithmeticException.class,
                () -> Seconds.ofHours(hoursThatOverflowWhenConvertedToSeconds));
    }
}
