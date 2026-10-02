package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#ofTaiSeconds(long, long)} rejects arguments
 * whose combined value overflows the supported range.
 */
public class TestTaiInstant_factory_ofTaiSeconds_long_long_tooBig {

    @Test
    public void factory_ofTaiSeconds_long_long_tooBig() {
        // Adding one extra second (1_000_000_000 nanos) to Long.MAX_VALUE seconds
        // overflows the second count, so the factory must throw.
        long taiSeconds = Long.MAX_VALUE;
        long nanoAdjustment = 1_000_000_000;

        assertThrows(
                ArithmeticException.class,
                () -> TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment));
    }
}
