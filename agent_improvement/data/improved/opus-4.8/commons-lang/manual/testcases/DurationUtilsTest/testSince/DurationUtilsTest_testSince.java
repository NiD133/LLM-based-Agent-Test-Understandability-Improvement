package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#since(java.time.temporal.Temporal)}, which returns the
 * {@link Duration} elapsed from a given start instant until "now".
 */
public class DurationUtilsTest_testSince extends AbstractLangTest {

    /**
     * Convenience: a Duration is non-negative when it compares greater than or equal to
     * {@link Duration#ZERO}.
     */
    private static boolean isNonNegative(final Duration duration) {
        return duration.compareTo(Duration.ZERO) >= 0;
    }

    /**
     * Convenience: a Duration is non-positive when it compares less than or equal to
     * {@link Duration#ZERO}.
     */
    private static boolean isNonPositive(final Duration duration) {
        return duration.compareTo(Duration.ZERO) <= 0;
    }

    @Test
    void testSince() {
        // A start in the past (the epoch) yields a non-negative elapsed duration.
        assertTrue(isNonNegative(DurationUtils.since(Instant.EPOCH)),
                "since(EPOCH) should be non-negative because the epoch is in the past");

        // The earliest representable instant is also in the past, so still non-negative.
        assertTrue(isNonNegative(DurationUtils.since(Instant.MIN)),
                "since(MIN) should be non-negative because MIN is in the past");

        // The latest representable instant is in the future, so the elapsed time is non-positive.
        assertTrue(isNonPositive(DurationUtils.since(Instant.MAX)),
                "since(MAX) should be non-positive because MAX is in the future");
    }
}
