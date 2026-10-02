package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Quarter#get(java.time.temporal.TemporalField)} rejects a null field.
 */
public class TestQuarter_test_get_null {

    @Test
    public void get_withNullField_throwsNullPointerException() {
        // Passing a null TemporalField to get(...) must fail fast with a NullPointerException.
        assertThrows(NullPointerException.class, () -> Quarter.Q2.get(null));
    }
}
