package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class DurationUtilsTest_testIsPositive extends AbstractLangTest {

    @Test
    void testIsPositive() {
        assertFalse(DurationUtils.isPositive(Duration.ZERO), "Zero is not a positive duration.");
        assertFalse(DurationUtils.isPositive(Duration.ofMillis(-1)), "A negative duration is not positive.");
        assertTrue(DurationUtils.isPositive(Duration.ofMillis(1)), "A duration greater than zero is positive.");
    }
}
