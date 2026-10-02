package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Duration;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testGetNanosOfMiili extends AbstractLangTest {

    // getNanosOfMiili returns the sub-millisecond nanosecond remainder:
    // result = duration.getNano() % 1_000_000, treating null as Duration.ZERO.
    private static final int NANOS_PER_MILLI = 1_000_000;

    @Test
    void testGetNanosOfMiili() {
        // null and zero durations have no sub-millisecond nanoseconds
        assertEquals(0, DurationUtils.getNanosOfMiili(null));
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ZERO));

        // sub-millisecond durations: nanosecond count is returned directly (no wrapping)
        assertEquals(1,       DurationUtils.getNanosOfMiili(Duration.ofNanos(1)));
        assertEquals(10,      DurationUtils.getNanosOfMiili(Duration.ofNanos(10)));
        assertEquals(100,     DurationUtils.getNanosOfMiili(Duration.ofNanos(100)));
        assertEquals(1_000,   DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000)));
        assertEquals(10_000,  DurationUtils.getNanosOfMiili(Duration.ofNanos(10_000)));
        assertEquals(100_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(100_000)));

        // exactly 1 ms (1_000_000 ns): remainder is 0
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ofNanos(NANOS_PER_MILLI)));

        // 1 ms + 1 ns: remainder is 1
        assertEquals(1, DurationUtils.getNanosOfMiili(Duration.ofNanos(NANOS_PER_MILLI + 1)));
    }
}
