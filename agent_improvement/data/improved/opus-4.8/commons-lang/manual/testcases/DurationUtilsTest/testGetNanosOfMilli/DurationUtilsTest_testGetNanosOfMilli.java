package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#getNanosOfMilli(Duration)}.
 * <p>
 * {@code getNanosOfMilli} returns the sub-millisecond portion of a duration's
 * nanosecond field, i.e. {@code duration.getNano() % 1_000_000}. The result is
 * therefore always in the range 0 to 999,999, and a {@code null} duration is
 * treated as {@link Duration#ZERO}.
 * </p>
 */
public class DurationUtilsTest_testGetNanosOfMilli extends AbstractLangTest {

    @Test
    void testGetNanosOfMilli() {
        // A null duration is treated as zero.
        assertEquals(0, DurationUtils.getNanosOfMilli(null));

        // Zero duration has no nanosecond remainder.
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ZERO));

        // Durations smaller than one millisecond are returned unchanged,
        // because the whole nanosecond value is below the 1,000,000 modulus.
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1)));
        assertEquals(10, DurationUtils.getNanosOfMilli(Duration.ofNanos(10)));
        assertEquals(100, DurationUtils.getNanosOfMilli(Duration.ofNanos(100)));
        assertEquals(1_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000)));
        assertEquals(10_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(10_000)));
        assertEquals(100_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(100_000)));

        // Exactly one millisecond leaves a sub-millisecond remainder of zero.
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_000)));

        // One millisecond plus one nanosecond leaves a remainder of one.
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_001)));
    }
}
