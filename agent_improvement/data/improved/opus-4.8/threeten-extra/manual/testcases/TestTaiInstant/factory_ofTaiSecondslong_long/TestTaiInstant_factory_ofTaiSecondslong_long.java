package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link TaiInstant#ofTaiSeconds(long, long)} factory method.
 * <p>
 * The factory normalises the nanosecond adjustment into the range 0 to 999,999,999,
 * carrying any overflow or borrow into the seconds field.
 */
public class TestTaiInstant_factory_ofTaiSecondslong_long {

    private static final int NANOS_PER_SECOND = 1_000_000_000;

    /**
     * Asserts that {@code ofTaiSeconds} produces an instant with the expected
     * normalised seconds and nano-of-second.
     */
    private static void assertOfTaiSeconds(
            long taiSeconds, long nanoAdjustment, long expectedSeconds, long expectedNanos) {
        TaiInstant instant = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);
        assertEquals(expectedSeconds, instant.getTaiSeconds());
        assertEquals(expectedNanos, instant.getNano());
    }

    @Test
    public void factory_ofTaiSecondslong_long() {
        for (long seconds = -2; seconds <= 2; seconds++) {
            // Nanos already in range [0, 9]: stored unchanged, seconds unchanged.
            for (int nanos = 0; nanos < 10; nanos++) {
                assertOfTaiSeconds(seconds, nanos, seconds, nanos);
            }
            // Negative nanos [-10, -1]: borrow one second, nanos wrap up by 1e9.
            for (int nanos = -10; nanos < 0; nanos++) {
                assertOfTaiSeconds(seconds, nanos, seconds - 1, nanos + NANOS_PER_SECOND);
            }
            // Nanos just below one second [999999990, 999999999]: stored unchanged.
            for (int nanos = 999999990; nanos < NANOS_PER_SECOND; nanos++) {
                assertOfTaiSeconds(seconds, nanos, seconds, nanos);
            }
        }
    }
}
