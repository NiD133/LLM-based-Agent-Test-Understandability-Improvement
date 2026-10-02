package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_plus_overflowTooSmall {

    @Test
    public void test_plus_overflowTooSmall() {
        UtcInstant minimumUtcInstant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        Duration oneNanosecondBeforeMinimum = Duration.ofNanos(-1);

        assertThrows(ArithmeticException.class, () -> minimumUtcInstant.plus(oneNanosecondBeforeMinimum));
    }
}
