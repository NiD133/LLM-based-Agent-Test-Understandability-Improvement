package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TaiInstant#ofTaiSeconds(long, long)}.
 *
 * The factory normalises the (seconds, nanoAdjustment) pair so that the
 * stored nano-of-second is always in [0, 999_999_999]:
 *   - a positive nanoAdjustment that is already in range is stored as-is
 *   - a negative nanoAdjustment is wrapped by borrowing one second
 *   - a large positive nanoAdjustment that is still in range is stored as-is
 */
public class TestTaiInstant_factory_ofTaiSecondslong_long {

    // Seconds values exercised by every sub-test (-2 … +2)
    private static final long[] TEST_SECONDS = {-2L, -1L, 0L, 1L, 2L};

    /**
     * A nanoAdjustment already in [0, 999_999_999] is stored unchanged; the
     * TAI-seconds component is not modified.
     */
    @Test
    public void factory_ofTaiSeconds_nonNegativeNanoInRange_storedAsIs() {
        for (long secs : TEST_SECONDS) {
            for (int nano = 0; nano < 10; nano++) {
                TaiInstant t = TaiInstant.ofTaiSeconds(secs, nano);
                assertEquals(secs, t.getTaiSeconds(),
                        "getTaiSeconds() should be unchanged when nano is in [0,999999999]");
                assertEquals(nano, t.getNano(),
                        "getNano() should equal the original nanoAdjustment when it is in [0,999999999]");
            }
        }
    }

    /**
     * A negative nanoAdjustment is normalised by borrowing one second:
     * stored_seconds = taiSeconds - 1, stored_nano = nanoAdjustment + 1_000_000_000.
     */
    @Test
    public void factory_ofTaiSeconds_negativeNano_borrowsOneSecond() {
        for (long secs : TEST_SECONDS) {
            for (int nano = -10; nano < 0; nano++) {
                TaiInstant t = TaiInstant.ofTaiSeconds(secs, nano);
                assertEquals(secs - 1, t.getTaiSeconds(),
                        "getTaiSeconds() should be decremented by 1 for a negative nanoAdjustment");
                assertEquals(nano + 1_000_000_000, t.getNano(),
                        "getNano() should be nanoAdjustment + 1_000_000_000 when nanoAdjustment is negative");
            }
        }
    }

    /**
     * A nanoAdjustment near the top of [0, 999_999_999] is stored unchanged; the
     * TAI-seconds component is not modified.
     */
    @Test
    public void factory_ofTaiSeconds_largeNanoStillInRange_storedAsIs() {
        for (long secs : TEST_SECONDS) {
            for (int nano = 999_999_990; nano < 1_000_000_000; nano++) {
                TaiInstant t = TaiInstant.ofTaiSeconds(secs, nano);
                assertEquals(secs, t.getTaiSeconds(),
                        "getTaiSeconds() should be unchanged when nano is near 999_999_999");
                assertEquals(nano, t.getNano(),
                        "getNano() should equal the original nanoAdjustment when it is near 999_999_999");
            }
        }
    }
}
