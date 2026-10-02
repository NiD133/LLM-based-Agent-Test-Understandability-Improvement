package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_minus_overflowTooBig {

    /**
     * Subtracting a duration that pushes the second count past {@code Long.MAX_VALUE}
     * must fail with an {@link ArithmeticException}.
     * <p>
     * The instant sits at the largest representable second. Subtracting
     * {@code Duration.ofSeconds(-1, 999999999)} effectively adds just over one
     * second, so the resulting second count overflows the {@code long} range.
     */
    @Test
    public void test_minus_overflowTooBig() {
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);
        Duration durationThatOverflows = Duration.ofSeconds(-1, 999999999);

        assertThrows(ArithmeticException.class, () -> maxInstant.minus(durationThatOverflows));
    }
}
