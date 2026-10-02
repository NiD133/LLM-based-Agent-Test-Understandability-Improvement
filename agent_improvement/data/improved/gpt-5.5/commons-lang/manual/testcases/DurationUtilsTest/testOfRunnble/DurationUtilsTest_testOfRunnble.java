package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testOfRunnble extends AbstractLangTest {

    private void testSince() {
    }

    @Test
    void testOfRunnble() {
        final Duration elapsed = DurationUtils.of(this::testSince);

        assertTrue(elapsed.compareTo(Duration.ZERO) >= 0);
    }
}
