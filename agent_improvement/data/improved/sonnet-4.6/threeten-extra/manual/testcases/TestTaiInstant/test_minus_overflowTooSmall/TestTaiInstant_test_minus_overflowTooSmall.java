package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooSmall {

    @Test
    public void test_minus_overflowTooSmall() {
        // Subtracting any positive amount from Long.MIN_VALUE seconds must overflow
        TaiInstant minimum = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> minimum.minus(Duration.ofSeconds(0, 1)));
    }
}
