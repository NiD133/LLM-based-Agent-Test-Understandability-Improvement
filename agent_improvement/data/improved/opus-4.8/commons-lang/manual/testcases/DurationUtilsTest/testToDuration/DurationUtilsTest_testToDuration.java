package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#toDuration(long, TimeUnit)}, which converts an
 * {@code amount} expressed in a given {@link TimeUnit} into an equivalent
 * {@link Duration}.
 */
public class DurationUtilsTest_testToDuration extends AbstractLangTest {

    @Test
    void testToDuration() {
        // Each supported TimeUnit maps to the matching Duration factory.
        assertEquals(Duration.ofDays(1), DurationUtils.toDuration(1, TimeUnit.DAYS));
        assertEquals(Duration.ofHours(1), DurationUtils.toDuration(1, TimeUnit.HOURS));
        assertEquals(Duration.ofMinutes(1), DurationUtils.toDuration(1, TimeUnit.MINUTES));
        assertEquals(Duration.ofSeconds(1), DurationUtils.toDuration(1, TimeUnit.SECONDS));
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1, TimeUnit.MILLISECONDS));
        assertEquals(Duration.ofNanos(1), DurationUtils.toDuration(1, TimeUnit.NANOSECONDS));

        // 1000 microseconds equal exactly 1 millisecond.
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1_000, TimeUnit.MICROSECONDS));

        // The sign and zero of the amount are preserved in the resulting Duration.
        assertEquals(1, DurationUtils.toDuration(1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(-1, DurationUtils.toDuration(-1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(0, DurationUtils.toDuration(0, TimeUnit.SECONDS).toMillis());
    }
}
