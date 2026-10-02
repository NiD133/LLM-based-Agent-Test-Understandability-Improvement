package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies the null-handling contract of {@link AmPm#get(java.time.temporal.TemporalField)}.
 */
public class TestAmPm_test_get_null {

    /**
     * Calling {@code get} with a {@code null} field must throw a {@link NullPointerException}.
     */
    @Test
    public void get_withNullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AmPm.PM.get(null));
    }
}
