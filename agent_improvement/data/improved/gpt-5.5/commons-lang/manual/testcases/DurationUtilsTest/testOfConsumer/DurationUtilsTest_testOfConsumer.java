package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testOfConsumer extends AbstractLangTest {

    @Test
    void testOfConsumer() {
        final Duration elapsed = DurationUtils.of(start -> assertTrue(start.compareTo(Instant.now()) <= 0));

        assertTrue(elapsed.compareTo(Duration.ZERO) >= 0);

        final Instant before = Instant.now();
        DurationUtils.of(start -> assertTrue(start.compareTo(before) >= 0));
    }
}
