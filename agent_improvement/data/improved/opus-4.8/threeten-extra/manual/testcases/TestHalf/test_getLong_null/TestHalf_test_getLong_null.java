package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#getLong(java.time.temporal.TemporalField)} rejects a null field.
 */
public class TestHalf_test_getLong_null {

    @Test
    public void getLong_withNullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Half.H2.getLong(null));
    }
}
