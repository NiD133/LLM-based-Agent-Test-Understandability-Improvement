package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DurationUtils.isPositive")
public class DurationUtilsTest_testIsPositive extends AbstractLangTest {

    @Test
    @DisplayName("returns false for Duration.ZERO (boundary: zero is not positive)")
    void testIsPositive_zero_returnsFalse() {
        assertFalse(DurationUtils.isPositive(Duration.ZERO));
    }

    @Test
    @DisplayName("returns false for a negative duration (boundary: negative is not positive)")
    void testIsPositive_negative_returnsFalse() {
        assertFalse(DurationUtils.isPositive(Duration.ofMillis(-1)));
    }

    @Test
    @DisplayName("returns true for a positive duration (boundary: smallest positive value)")
    void testIsPositive_positive_returnsTrue() {
        assertTrue(DurationUtils.isPositive(Duration.ofMillis(1)));
    }
}
