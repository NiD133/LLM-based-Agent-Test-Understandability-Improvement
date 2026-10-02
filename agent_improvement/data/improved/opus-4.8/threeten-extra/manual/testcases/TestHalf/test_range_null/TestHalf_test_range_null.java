package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Half#range(TemporalField)} rejects a null field.
 */
public class TestHalf_test_range_null {

    @Test
    public void range_withNullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Half.H1.range(null));
    }
}
