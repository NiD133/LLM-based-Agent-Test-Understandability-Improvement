package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooBig {

    // Subtracting a negative duration from MAX_VALUE causes seconds to exceed Long.MAX_VALUE,
    // which Math.subtractExact detects and throws ArithmeticException.
    @Test
    public void test_minus_overflowTooBig() {
        TaiInstant i = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);
        assertThrows(ArithmeticException.class, () -> i.minus(Duration.ofSeconds(-1, 999999999)));
    }
}
