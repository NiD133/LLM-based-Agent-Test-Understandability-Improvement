package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#ofTaiSeconds(long, long)} when a negative nanosecond
 * adjustment is normalised back into the valid 0..999,999,999 range.
 */
public class TestTaiInstant_factory_ofTaiSeconds_long_long_nanosNegativeAdjusted {

    @Test
    public void factory_ofTaiSeconds_long_long_nanosNegativeAdjusted() {
        // A negative nano adjustment borrows one whole second:
        // 2 seconds minus 1 nanosecond == 1 second plus 999,999,999 nanoseconds.
        TaiInstant test = TaiInstant.ofTaiSeconds(2L, -1);

        assertEquals(1, test.getTaiSeconds());
        assertEquals(999999999, test.getNano());
    }
}
