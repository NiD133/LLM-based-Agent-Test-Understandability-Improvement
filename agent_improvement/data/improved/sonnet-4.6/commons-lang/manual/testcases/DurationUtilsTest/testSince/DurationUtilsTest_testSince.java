package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.time.Instant;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testSince extends AbstractLangTest {

    // DurationUtils.since(start) returns Duration.between(start, Instant.now()).
    // A past start yields a positive (>= 0) duration; a future start yields a negative (<= 0) duration.

    @Test
    void testSince_pastInstant_epochIsInThePast_durationIsNonNegative() {
        // Instant.EPOCH is 1970-01-01T00:00:00Z — always in the past, so elapsed time >= 0
        Duration sinceEpoch = DurationUtils.since(Instant.EPOCH);
        assertTrue(sinceEpoch.compareTo(Duration.ZERO) >= 0);
    }

    @Test
    void testSince_pastInstant_minIsTheFarthestPastInstant_durationIsNonNegative() {
        // Instant.MIN is the earliest representable moment — always in the past, so elapsed time >= 0
        Duration sinceMin = DurationUtils.since(Instant.MIN);
        assertTrue(sinceMin.compareTo(Duration.ZERO) >= 0);
    }

    @Test
    void testSince_futureInstant_maxIsTheFarthestFutureInstant_durationIsNonPositive() {
        // Instant.MAX is the latest representable moment — always in the future, so elapsed time <= 0
        Duration sinceMax = DurationUtils.since(Instant.MAX);
        assertTrue(sinceMax.compareTo(Duration.ZERO) <= 0);
    }
}
