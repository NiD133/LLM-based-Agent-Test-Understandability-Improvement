package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_plus_overflowTooSmall {

    @Test
    public void test_plus_overflowTooSmall() {
        // Adding a negative duration (-1s + 999999999ns = -1ns) to the minimum TAI instant
        // causes a long underflow in the seconds field, so ArithmeticException must be thrown.
        TaiInstant i = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> i.plus(Duration.ofSeconds(-1, 999999999)));
    }
}
