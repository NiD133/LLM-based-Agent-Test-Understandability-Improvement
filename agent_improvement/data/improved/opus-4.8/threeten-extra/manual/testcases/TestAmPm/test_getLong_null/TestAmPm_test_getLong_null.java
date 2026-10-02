package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AmPm#getLong(java.time.temporal.TemporalField)} rejects a
 * {@code null} field by throwing a {@link NullPointerException}.
 */
public class TestAmPm_test_getLong_null {

    @Test
    public void getLong_withNullField_throwsNullPointerException() {
        //noinspection DataFlowIssue - intentionally passing null to verify the null check
        assertThrows(NullPointerException.class, () -> AmPm.PM.getLong(null));
    }
}
