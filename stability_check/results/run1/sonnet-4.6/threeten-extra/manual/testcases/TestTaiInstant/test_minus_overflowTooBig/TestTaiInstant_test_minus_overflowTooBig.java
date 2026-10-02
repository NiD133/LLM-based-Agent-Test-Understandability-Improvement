package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooBig {

    /**
     * Subtracting a negative duration whose magnitude would push the result past Long.MAX_VALUE
     * must throw ArithmeticException rather than silently wrap around.
     */
    @Test
    public void test_minus_overflowTooBig() {
        // Instant at the very top of the representable range
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);

        // Duration.ofSeconds(-1, 999999999) represents -1s + 999_999_999ns = effectively ~0s,
        // but subtracting it (i.e. adding ~0 to MAX) would still overflow during intermediate
        // arithmetic inside minus(), triggering ArithmeticException.
        assertThrows(ArithmeticException.class,
                () -> maxInstant.minus(Duration.ofSeconds(-1, 999999999)));
    }
}
