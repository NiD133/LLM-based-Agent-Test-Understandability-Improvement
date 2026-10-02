package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testToDuration extends AbstractLangTest {

    @Test
    void testToDuration() {
        assertConvertsToDuration(1, TimeUnit.DAYS, Duration.ofDays(1));
        assertConvertsToDuration(1, TimeUnit.HOURS, Duration.ofHours(1));
        assertConvertsToDuration(1_000, TimeUnit.MICROSECONDS, Duration.ofMillis(1));
        assertConvertsToDuration(1, TimeUnit.MILLISECONDS, Duration.ofMillis(1));
        assertConvertsToDuration(1, TimeUnit.MINUTES, Duration.ofMinutes(1));
        assertConvertsToDuration(1, TimeUnit.NANOSECONDS, Duration.ofNanos(1));
        assertConvertsToDuration(1, TimeUnit.SECONDS, Duration.ofSeconds(1));

        assertEquals(1, DurationUtils.toDuration(1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(-1, DurationUtils.toDuration(-1, TimeUnit.MILLISECONDS).toMillis());
        assertEquals(0, DurationUtils.toDuration(0, TimeUnit.SECONDS).toMillis());
    }

    private void assertConvertsToDuration(final long amount, final TimeUnit timeUnit, final Duration expectedDuration) {
        assertEquals(expectedDuration, DurationUtils.toDuration(amount, timeUnit));
    }
}
