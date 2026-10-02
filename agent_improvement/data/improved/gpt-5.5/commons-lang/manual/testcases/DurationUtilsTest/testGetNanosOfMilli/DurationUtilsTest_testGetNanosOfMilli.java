package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testGetNanosOfMilli extends AbstractLangTest {

    @Test
    void testGetNanosOfMilli() {
        assertNanosOfMilli(0, null);
        assertNanosOfMilli(0, Duration.ZERO);

        assertNanosOfMilli(1, Duration.ofNanos(1));
        assertNanosOfMilli(10, Duration.ofNanos(10));
        assertNanosOfMilli(100, Duration.ofNanos(100));
        assertNanosOfMilli(1_000, Duration.ofNanos(1_000));
        assertNanosOfMilli(10_000, Duration.ofNanos(10_000));
        assertNanosOfMilli(100_000, Duration.ofNanos(100_000));

        assertNanosOfMilli(0, Duration.ofNanos(1_000_000));
        assertNanosOfMilli(1, Duration.ofNanos(1_000_001));
    }

    private static void assertNanosOfMilli(final int expectedNanos, final Duration duration) {
        assertEquals(expectedNanos, DurationUtils.getNanosOfMilli(duration));
    }
}
