package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#getNanosOfMiili(Duration)}.
 * <p>
 * The method returns the sub-millisecond part of a Duration's nanoseconds,
 * i.e. {@code duration.getNano() % 1_000_000}, yielding a value between 0 and 999,999.
 * A {@code null} duration is treated as {@link Duration#ZERO}.
 * </p>
 */
public class DurationUtilsTest_testGetNanosOfMiili extends AbstractLangTest {

    @Test
    void testGetNanosOfMiili() {
        // A null duration is treated as zero.
        assertEquals(0, DurationUtils.getNanosOfMiili(null));
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ZERO));

        // Durations smaller than one millisecond are returned unchanged,
        // because their whole nanosecond value is the sub-millisecond remainder.
        assertEquals(1, DurationUtils.getNanosOfMiili(Duration.ofNanos(1)));
        assertEquals(10, DurationUtils.getNanosOfMiili(Duration.ofNanos(10)));
        assertEquals(100, DurationUtils.getNanosOfMiili(Duration.ofNanos(100)));
        assertEquals(1_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000)));
        assertEquals(10_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(10_000)));
        assertEquals(100_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(100_000)));

        // Exactly one millisecond has no sub-millisecond remainder.
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_000)));

        // One millisecond plus one nanosecond leaves a remainder of one nanosecond.
        assertEquals(1, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_001)));
    }
}
