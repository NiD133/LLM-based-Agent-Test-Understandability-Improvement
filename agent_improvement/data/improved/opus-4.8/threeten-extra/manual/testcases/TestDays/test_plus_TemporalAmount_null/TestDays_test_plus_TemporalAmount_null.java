package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Days#plus(java.time.temporal.TemporalAmount)} rejects a null argument.
 */
public class TestDays_test_plus_TemporalAmount_null {

    @Test
    public void plus_nullTemporalAmount_throwsNullPointerException() {
        Days days = Days.of(Integer.MIN_VALUE + 1);

        //noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> days.plus(null));
    }
}
