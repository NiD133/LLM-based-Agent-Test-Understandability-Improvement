package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_minus_overflowTooSmall {

    @Test
    public void test_minus_overflowTooSmall() {
        UtcInstant minimumUtcInstant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);

        assertThrows(
                ArithmeticException.class,
                () -> minimumUtcInstant.minus(Duration.ofNanos(1)));
    }
}
