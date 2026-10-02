package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testGetNanosOfMiili extends AbstractLangTest {

    @Test
    void testGetNanosOfMiili() {
        assertNanosWithinCurrentMilli(0, null);
        assertNanosWithinCurrentMilli(0, Duration.ZERO);
        assertNanosWithinCurrentMilli(1, Duration.ofNanos(1));
        assertNanosWithinCurrentMilli(10, Duration.ofNanos(10));
        assertNanosWithinCurrentMilli(100, Duration.ofNanos(100));
        assertNanosWithinCurrentMilli(1_000, Duration.ofNanos(1_000));
        assertNanosWithinCurrentMilli(10_000, Duration.ofNanos(10_000));
        assertNanosWithinCurrentMilli(100_000, Duration.ofNanos(100_000));
        assertNanosWithinCurrentMilli(0, Duration.ofNanos(1_000_000));
        assertNanosWithinCurrentMilli(1, Duration.ofNanos(1_000_001));
    }

    private static void assertNanosWithinCurrentMilli(final int expectedNanos, final Duration duration) {
        assertEquals(expectedNanos, DurationUtils.getNanosOfMiili(duration));
    }
}
