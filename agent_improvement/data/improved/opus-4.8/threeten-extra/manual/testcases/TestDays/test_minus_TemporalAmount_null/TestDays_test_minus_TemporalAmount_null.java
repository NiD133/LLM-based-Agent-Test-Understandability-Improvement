package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#minus(java.time.temporal.TemporalAmount)} rejects a
 * {@code null} argument by throwing a {@link NullPointerException}.
 */
public class TestDays_test_minus_TemporalAmount_null {

    @Test
    public void minus_null_temporalAmount_throwsNullPointerException() {
        Days days = Days.of(Integer.MIN_VALUE + 1);

        //noinspection DataFlowIssue - intentionally passing null to test null-handling
        assertThrows(NullPointerException.class, () -> days.minus(null));
    }
}
