package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testOfConsumer extends AbstractLangTest {

    @Test
    void testOfConsumer() {
        // The consumer receives the start Instant captured just before execution begins.
        // Verify that start is not in the future (it must be <= now at time of check).
        Duration elapsed = DurationUtils.of(start -> {
            Instant checkTime = Instant.now();
            assertTrue(start.compareTo(checkTime) <= 0,
                "start instant should be at or before the current time inside the consumer");
        });
        assertTrue(elapsed.compareTo(Duration.ZERO) >= 0,
            "returned duration should be non-negative");

        // Verify that start is at or after a timestamp recorded before the call,
        // confirming that DurationUtils.of() does not use a stale pre-captured instant.
        Instant beforeCall = Instant.now();
        DurationUtils.of(start ->
            assertTrue(start.compareTo(beforeCall) >= 0,
                "start instant should be at or after the instant captured before the call"));
    }
}
