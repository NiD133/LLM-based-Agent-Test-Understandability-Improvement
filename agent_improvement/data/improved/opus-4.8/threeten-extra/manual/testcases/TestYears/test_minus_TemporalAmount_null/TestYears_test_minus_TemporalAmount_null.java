package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#minus(java.time.temporal.TemporalAmount)} rejects a
 * {@code null} amount by throwing a {@link NullPointerException}.
 */
public class TestYears_test_minus_TemporalAmount_null {

    @Test
    public void minus_nullTemporalAmount_throwsNullPointerException() {
        Years years = Years.of(Integer.MIN_VALUE + 1);

        //noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> years.minus(null));
    }
}
