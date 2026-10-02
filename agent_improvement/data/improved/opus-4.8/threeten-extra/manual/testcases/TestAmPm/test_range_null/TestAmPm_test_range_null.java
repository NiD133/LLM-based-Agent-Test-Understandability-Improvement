package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AmPm#range(java.time.temporal.TemporalField)} rejects a null field.
 */
public class TestAmPm_test_range_null {

    @Test
    public void range_withNullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AmPm.AM.range(null));
    }
}
