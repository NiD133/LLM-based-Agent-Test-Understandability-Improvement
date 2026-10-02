package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#toMillisLong(Duration)}.
 *
 * <p>{@code toMillisLong} converts a {@link Duration} to milliseconds, but instead of
 * throwing an {@link ArithmeticException} on overflow it saturates (clamps) the result to
 * {@link Long#MIN_VALUE} or {@link Long#MAX_VALUE}.</p>
 */
public class DurationUtilsTest_testToMillisLong extends AbstractLangTest {

    @Test
    void testToMillisLong() {
        // Ordinary durations: milliseconds are returned unchanged.
        assertEquals(0, DurationUtils.toMillisLong(Duration.ZERO));
        assertEquals(1, DurationUtils.toMillisLong(Duration.ofMillis(1)));
        assertEquals(-1, DurationUtils.toMillisLong(Duration.ofMillis(-1)));

        // Boundary durations expressed directly in millis: still fit, returned unchanged.
        assertEquals(Long.MIN_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MIN_VALUE)));
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofMillis(Long.MAX_VALUE)));

        // Overflow case: Long.MAX_VALUE seconds is far more than Long.MAX_VALUE millis,
        // so the result saturates to Long.MAX_VALUE instead of throwing.
        assertEquals(Long.MAX_VALUE, DurationUtils.toMillisLong(Duration.ofSeconds(Long.MAX_VALUE)));
    }
}
