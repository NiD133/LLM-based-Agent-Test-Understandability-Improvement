package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#toMillisInt(Duration)}.
 * <p>
 * {@code toMillisInt} converts a {@link Duration} to milliseconds as an {@code int}, clamping any
 * value that does not fit into the {@code int} range to {@link Integer#MIN_VALUE} or
 * {@link Integer#MAX_VALUE}.
 * </p>
 */
public class DurationUtilsTest_testToMillisInt extends AbstractLangTest {

    /** The largest {@code long} millisecond value that still fits into an {@code int}. */
    private static final long INT_MAX_AS_LONG = NumberUtils.LONG_INT_MAX_VALUE;

    /** The smallest {@code long} millisecond value that still fits into an {@code int}. */
    private static final long INT_MIN_AS_LONG = NumberUtils.LONG_INT_MIN_VALUE;

    @Test
    void testToMillisInt() {
        // Values that already fit into an int are returned unchanged.
        assertEquals(0, DurationUtils.toMillisInt(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisInt(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisInt(Duration.ofMillis(-1)));

        // The exact int boundaries map to themselves.
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MAX_VALUE)));

        // Just beyond the int range, the result is clamped to the nearest int boundary.
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(INT_MAX_AS_LONG + 1)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(INT_MAX_AS_LONG + 2)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(INT_MIN_AS_LONG - 1)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(INT_MIN_AS_LONG - 2)));

        // Extreme nanosecond-based durations also clamp to the int boundaries.
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MAX_VALUE)));
    }
}
