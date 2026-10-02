package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DurationUtils#toMillisInt(Duration)} saturates to
 * {@link Integer#MAX_VALUE} when the duration is far too large to fit in an int.
 */
public class DurationUtilsTest_testToMillisIntOverflowToMaxInteger extends AbstractLangTest {

    @Test
    void testToMillisIntOverflowToMaxInteger() {
        // A duration of (Long.MAX_VALUE / 1000 + 1) seconds is so large that converting
        // it to milliseconds overflows a long. DurationUtils clamps such overflow first to
        // Long.MAX_VALUE, then narrows that to the int range, yielding Integer.MAX_VALUE.
        final Duration overflowingDuration = Duration.ofSeconds(Long.MAX_VALUE / 1000 + 1);

        final int actualMillis = DurationUtils.toMillisInt(overflowingDuration);

        assertEquals(Integer.MAX_VALUE, actualMillis);
    }
}
