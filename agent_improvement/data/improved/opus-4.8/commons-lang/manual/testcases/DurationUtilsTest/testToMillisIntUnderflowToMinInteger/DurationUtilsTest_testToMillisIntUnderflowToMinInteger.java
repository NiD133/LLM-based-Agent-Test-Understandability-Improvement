package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DurationUtils#toMillisInt(Duration)} clamps to
 * {@link Integer#MIN_VALUE} when the duration's milliseconds underflow the
 * {@code int} range, instead of overflowing or throwing.
 */
public class DurationUtilsTest_testToMillisIntUnderflowToMinInteger extends AbstractLangTest {

    @Test
    void testToMillisIntUnderflowToMinInteger() {
        // Pick a duration whose milliseconds are far below Integer.MIN_VALUE.
        // Long.MIN_VALUE / 1000 seconds is roughly the most negative duration
        // expressible in milliseconds; subtracting one more second pushes it
        // safely past the negative int range.
        final Duration durationBelowIntRange = Duration.ofSeconds(Long.MIN_VALUE / 1000 - 1);

        // The result must be clamped to the smallest int rather than wrapping around.
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(durationBelowIntRange));
    }
}
