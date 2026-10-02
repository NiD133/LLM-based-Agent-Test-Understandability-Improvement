package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testOfRunnble extends AbstractLangTest {

    /** Exercises {@link DurationUtils#since(java.time.temporal.Temporal)} to provide a measurable runnable. */
    void testSince() {
        DurationUtils.since(Instant.now());
    }

    @Test
    void testOfRunnable() {
        Duration executionDuration = DurationUtils.of(this::testSince);
        assertTrue(executionDuration.compareTo(Duration.ZERO) >= 0,
            "Execution duration measured by DurationUtils.of(FailableRunnable) must be non-negative");
    }
}
