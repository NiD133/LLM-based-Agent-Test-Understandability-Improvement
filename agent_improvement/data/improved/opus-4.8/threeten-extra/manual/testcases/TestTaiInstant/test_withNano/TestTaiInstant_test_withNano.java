package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link TaiInstant#withNano(int)}.
 */
public class TestTaiInstant_test_withNano {

    /**
     * Cases for {@link #test_withNano}.
     * <p>
     * Each row is: starting TAI seconds, starting nano adjustment, the nano-of-second
     * passed to {@code withNano}, the expected TAI seconds of the result (or {@code null}
     * when the call is expected to fail), and the expected nano-of-second of the result.
     * <p>
     * {@code withNano} only accepts values in the range 0 to 999,999,999, so the last two
     * rows (a negative value and one equal to 1,000,000,000) are expected to throw.
     */
    public static Object[][] data_withNano() {
        return new Object[][] {
            //   taiSeconds, nanoAdjustment, newNano,     expectedTaiSeconds, expectedNano
            { 0L,   12345L, 1,         (Long) 0L,   1L },
            { 7L,   12345L, 2,         (Long) 7L,   2L },
            { -99L, 12345L, 3,         (Long) (-99L), 3L },
            { -99L, 12345L, 999999999, (Long) (-99L), 999999999L },
            // newNano below the valid range -> IllegalArgumentException
            { -99L, 12345L, -1,        null,        0L },
            // newNano at/above the valid range -> IllegalArgumentException
            { -99L, 12345L, 1000000000, null,       0L },
        };
    }

    @ParameterizedTest
    @MethodSource("data_withNano")
    public void test_withNano(long taiSeconds, long nanoAdjustment, int newNano,
            @Nullable Long expectedTaiSeconds, long expectedNano) {
        TaiInstant start = TaiInstant.ofTaiSeconds(taiSeconds, nanoAdjustment);

        if (expectedTaiSeconds != null) {
            TaiInstant result = start.withNano(newNano);
            assertEquals(expectedTaiSeconds.longValue(), result.getTaiSeconds());
            assertEquals(expectedNano, result.getNano());
        } else {
            assertThrows(IllegalArgumentException.class, () -> start.withNano(newNano));
        }
    }
}
