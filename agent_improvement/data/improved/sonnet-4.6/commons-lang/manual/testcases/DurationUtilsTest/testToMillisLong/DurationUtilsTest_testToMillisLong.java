package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisLong extends AbstractLangTest {

    @Test
    void testToMillisLong() {
        // Zero duration maps to zero milliseconds
        assertEquals(0, DurationUtils.toMillisLong(Duration.ZERO));

        // Ordinary positive and negative millisecond values are returned unchanged
        assertEquals(1, DurationUtils.toMillisLong(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisLong(Duration.ofMillis(-1)));

        // Durations that exactly fit within the long range are returned as-is
        assertEquals(Long.MIN_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MIN_VALUE)));
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MAX_VALUE)));

        // A duration whose millisecond equivalent overflows long is clamped to Long.MAX_VALUE
        Duration overflowingDuration = Duration.ofSeconds(Long.MAX_VALUE);
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(overflowingDuration));
    }
}
