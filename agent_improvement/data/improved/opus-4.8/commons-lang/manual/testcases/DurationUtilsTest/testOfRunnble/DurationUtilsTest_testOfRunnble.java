package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#of(org.apache.commons.lang3.function.FailableRunnable)}.
 */
public class DurationUtilsTest_testOfRunnble extends AbstractLangTest {

    /**
     * Timing how long a piece of work takes must never yield a negative duration.
     */
    @Test
    void testOfRunnble() {
        // Pass a Runnable (this::doWork) so the FailableRunnable overload of of() is exercised.
        final Duration elapsed = DurationUtils.of(this::doWork);

        assertTrue(elapsed.compareTo(Duration.ZERO) >= 0,
                "Measured execution time should be non-negative");
    }

    /**
     * The work whose execution time is being measured.
     */
    private void doWork() {
        assertTrue(DurationUtils.since(Instant.EPOCH).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MIN).compareTo(Duration.ZERO) >= 0);
        assertTrue(DurationUtils.since(Instant.MAX).compareTo(Duration.ZERO) <= 0);
    }
}
