package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#minus(java.time.temporal.TemporalAmount)} rejects a null argument.
 */
public class TestHours_test_minus_TemporalAmount_null {

    @Test
    public void minus_nullTemporalAmount_throwsNullPointerException() {
        Hours oneHour = Hours.of(Integer.MIN_VALUE + 1);

        //noinspection DataFlowIssue - intentionally passing null to verify the null-check
        assertThrows(NullPointerException.class, () -> oneHour.minus(null));
    }
}
