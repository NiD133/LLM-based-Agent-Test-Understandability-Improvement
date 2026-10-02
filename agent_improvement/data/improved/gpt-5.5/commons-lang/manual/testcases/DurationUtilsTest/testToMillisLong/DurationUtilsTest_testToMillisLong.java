package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToMillisLong extends AbstractLangTest {

    @Test
    void testToMillisLong() {
        assertMillisLongEquals(0, Duration.ZERO);
        assertMillisLongEquals(1, Duration.ofMillis(1));
        assertMillisLongEquals(-1, Duration.ofMillis(-1));
        assertMillisLongEquals(Long.MIN_VALUE, Duration.ofMillis(Long.MIN_VALUE));
        assertMillisLongEquals(Long.MAX_VALUE, Duration.ofMillis(Long.MAX_VALUE));
        assertMillisLongEquals(Long.MAX_VALUE, Duration.ofSeconds(Long.MAX_VALUE));
    }

    private static void assertMillisLongEquals(final long expectedMillis, final Duration duration) {
        assertEquals(expectedMillis, DurationUtils.toMillisLong(duration));
    }
}
