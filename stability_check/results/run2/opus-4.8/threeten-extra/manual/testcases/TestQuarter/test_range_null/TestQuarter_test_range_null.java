package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#range(java.time.temporal.TemporalField)} rejects a null field.
 */
public class TestQuarter_test_range_null {

    @Test
    public void range_withNullField_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Quarter.Q1.range(null));
    }
}
