package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testGetNanosOfMilli extends AbstractLangTest {

    /**
     * Verifies that getNanosOfMilli returns the sub-millisecond nanosecond remainder
     * (i.e. nanos % 1,000,000), handling null and zero durations as well as
     * boundary values around the 1-millisecond mark.
     */
    @Test
    void testGetNanosOfMilli() {
        // Null and zero-duration inputs should both return 0
        assertEquals(0, DurationUtils.getNanosOfMilli(null),
                "null duration should yield 0 nanoseconds");
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ZERO),
                "zero duration should yield 0 nanoseconds");

        // Durations well under 1 ms: the nano value is returned as-is
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1)),
                "1 ns < 1 ms, so remainder is 1");
        assertEquals(10, DurationUtils.getNanosOfMilli(Duration.ofNanos(10)),
                "10 ns < 1 ms, so remainder is 10");
        assertEquals(100, DurationUtils.getNanosOfMilli(Duration.ofNanos(100)),
                "100 ns < 1 ms, so remainder is 100");
        assertEquals(1_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000)),
                "1,000 ns < 1 ms, so remainder is 1,000");
        assertEquals(10_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(10_000)),
                "10,000 ns < 1 ms, so remainder is 10,000");
        assertEquals(100_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(100_000)),
                "100,000 ns < 1 ms, so remainder is 100,000");

        // Exactly 1 ms (1,000,000 ns): the modulo operation yields 0
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_000)),
                "1,000,000 ns is exactly 1 ms, so remainder is 0");

        // 1 ms + 1 ns: only the sub-millisecond part (1 ns) is returned
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_001)),
                "1,000,001 ns is 1 ms plus 1 ns, so remainder is 1");
    }
}
