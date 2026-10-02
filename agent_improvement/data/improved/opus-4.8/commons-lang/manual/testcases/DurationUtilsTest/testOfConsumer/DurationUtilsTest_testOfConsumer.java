package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link DurationUtils#of(org.apache.commons.lang3.function.FailableConsumer)}.
 *
 * <p>{@code DurationUtils.of(consumer)} captures a start instant, passes it to the
 * given consumer, and returns the elapsed {@link Duration} from that start until
 * the consumer finished running.</p>
 */
public class DurationUtilsTest_testOfConsumer extends AbstractLangTest {

    @Test
    void testOfConsumer() {
        // The elapsed duration of running a consumer is never negative.
        final Duration elapsed = DurationUtils.of(start ->
                assertTrue(start.compareTo(Instant.now()) <= 0,
                        "start instant should be at or before 'now'"));
        assertTrue(elapsed.compareTo(Duration.ZERO) >= 0,
                "measured duration should be zero or positive");

        // The start instant handed to the consumer is captured after this point,
        // so it must be at or after 'before'.
        final Instant before = Instant.now();
        DurationUtils.of(start ->
                assertTrue(start.compareTo(before) >= 0,
                        "start instant should be at or after the moment captured before the call"));
    }
}
