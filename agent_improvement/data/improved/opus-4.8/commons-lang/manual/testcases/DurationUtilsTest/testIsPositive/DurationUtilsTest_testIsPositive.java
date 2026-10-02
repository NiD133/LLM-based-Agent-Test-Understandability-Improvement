package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#isPositive(Duration)}.
 *
 * <p>A duration is considered positive only when it is strictly greater than zero,
 * i.e. it is neither negative nor zero.</p>
 */
public class DurationUtilsTest_testIsPositive extends AbstractLangTest {

    @Test
    void testIsPositive() {
        // A zero duration is not positive.
        assertFalse(DurationUtils.isPositive(Duration.ZERO));

        // A negative duration is not positive.
        assertFalse(DurationUtils.isPositive(Duration.ofMillis(-1)));

        // A strictly greater-than-zero duration is positive.
        assertTrue(DurationUtils.isPositive(Duration.ofMillis(1)));
    }
}
