package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link DurationUtils#toMillisInt(Duration)}, which converts a Duration to
 * milliseconds clamped to the int range [Integer.MIN_VALUE, Integer.MAX_VALUE].
 */
public class DurationUtilsTest_testToMillisInt extends AbstractLangTest {

    @Test
    void testToMillisInt() {
        // Zero and single-unit values convert exactly
        assertEquals(0, DurationUtils.toMillisInt(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisInt(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisInt(Duration.ofMillis(-1)));

        // Values at the exact int boundary convert without clamping
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MAX_VALUE)));

        // Values just beyond the int boundary are clamped to Integer.MAX_VALUE / Integer.MIN_VALUE
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 1)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 2)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 1)));
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 2)));

        // Extreme nanosecond-based durations that overflow long milliseconds are also clamped
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MIN_VALUE)));
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MAX_VALUE)));
    }
}
