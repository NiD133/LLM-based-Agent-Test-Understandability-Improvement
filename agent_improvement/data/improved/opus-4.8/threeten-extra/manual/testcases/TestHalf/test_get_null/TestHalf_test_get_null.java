package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#get(java.time.temporal.TemporalField)} rejects a null field.
 */
public class TestHalf_test_get_null {

    @Test
    public void get_withNullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Half.H2.get(null));
    }
}
