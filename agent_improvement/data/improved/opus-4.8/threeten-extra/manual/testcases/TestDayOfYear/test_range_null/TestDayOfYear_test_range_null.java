package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DayOfYear#range(TemporalField)} rejects a {@code null} field.
 */
public class TestDayOfYear_test_range_null {

    /** An arbitrary day-of-year instance to invoke {@code range} on. */
    private static final DayOfYear SAMPLE_DAY = DayOfYear.of(12);

    @Test
    public void range_withNullField_throwsNullPointerException() {
        assertThrows(
                NullPointerException.class,
                () -> SAMPLE_DAY.range((TemporalField) null));
    }
}
