package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_plus_overflowTooBig {

    @Test
    public void test_plus_overflowTooBig() {
        // Instant at the maximum TAI value: Long.MAX_VALUE seconds with 999_999_999 nanos
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999_999_999);

        // Adding even 1 nanosecond must overflow and throw ArithmeticException
        assertThrows(ArithmeticException.class, () -> maxInstant.plus(Duration.ofSeconds(0, 1)));
    }
}
