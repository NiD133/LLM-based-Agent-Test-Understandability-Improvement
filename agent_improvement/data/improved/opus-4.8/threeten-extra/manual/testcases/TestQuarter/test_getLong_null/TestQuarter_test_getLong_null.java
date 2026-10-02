package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#getLong(java.time.temporal.TemporalField)} rejects a
 * null field by throwing a {@link NullPointerException}.
 */
public class TestQuarter_test_getLong_null {

    @Test
    public void getLong_withNullField_throwsNullPointerException() {
        //noinspection DataFlowIssue - intentionally passing null to verify the failure
        assertThrows(NullPointerException.class, () -> Quarter.Q2.getLong(null));
    }
}
