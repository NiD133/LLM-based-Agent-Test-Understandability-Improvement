package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Hours#plus(java.time.temporal.TemporalAmount)} rejects a
 * {@code null} argument by throwing a {@link NullPointerException}.
 */
public class TestHours_test_plus_TemporalAmount_null {

    @Test
    public void plus_withNullTemporalAmount_throwsNullPointerException() {
        Hours hours = Hours.of(Integer.MIN_VALUE + 1);

        //noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> hours.plus(null));
    }
}
