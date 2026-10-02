package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooBig {

    /**
     * Subtracting a negative duration pushes the instant past the maximum
     * supported second value, so {@code minus} must fail with an
     * {@link ArithmeticException} rather than silently overflowing.
     */
    @Test
    public void test_minus_overflowTooBig() {
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);
        Duration subtractingPastMax = Duration.ofSeconds(-1, 999999999);

        assertThrows(ArithmeticException.class, () -> maxInstant.minus(subtractingPastMax));
    }
}
