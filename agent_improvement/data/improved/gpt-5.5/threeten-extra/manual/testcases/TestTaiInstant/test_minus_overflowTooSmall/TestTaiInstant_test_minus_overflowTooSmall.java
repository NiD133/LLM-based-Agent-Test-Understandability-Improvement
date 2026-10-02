package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooSmall {

    @Test
    public void test_minus_overflowTooSmall() {
        TaiInstant minimumTaiInstant = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);
        Duration oneNanosecond = Duration.ofSeconds(0, 1);

        assertThrows(ArithmeticException.class, () -> minimumTaiInstant.minus(oneNanosecond));
    }
}
