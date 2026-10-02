package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#minus(java.time.temporal.TemporalAmount)} rejects a
 * null argument by throwing a {@link NullPointerException}.
 */
public class TestMonths_test_minus_TemporalAmount_null {

    @Test
    public void minus_nullTemporalAmount_throwsNullPointerException() {
        Months months = Months.of(Integer.MIN_VALUE + 1);

        //noinspection DataFlowIssue - deliberately passing null to verify the null check
        assertThrows(NullPointerException.class, () -> months.minus(null));
    }
}
