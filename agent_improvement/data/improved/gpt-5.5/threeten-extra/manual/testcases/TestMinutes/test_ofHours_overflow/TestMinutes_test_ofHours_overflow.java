package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ofHours_overflow {

    @Test
    public void test_ofHours_overflow() {
        int hoursThatOverflowWhenConvertedToMinutes = (Integer.MAX_VALUE / 60) + 60;

        assertThrows(
                ArithmeticException.class,
                () -> Minutes.ofHours(hoursThatOverflowWhenConvertedToMinutes));
    }
}
