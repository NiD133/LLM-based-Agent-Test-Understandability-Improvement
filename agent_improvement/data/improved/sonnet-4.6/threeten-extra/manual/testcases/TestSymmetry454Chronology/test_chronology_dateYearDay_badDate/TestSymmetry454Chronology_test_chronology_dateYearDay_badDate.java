package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology#dateYearDay(int, int)} rejects a day-of-year
 * value that falls outside the valid range for the given year.
 *
 * <p>Normal (non-leap) years in the Symmetry454 calendar have exactly 364 days,
 * so day 365 is always invalid for a non-leap year such as 2000.
 */
@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_chronology_dateYearDay_badDate {

    @Test
    public void test_chronology_dateYearDay_badDate() {
        // Year 2000 is not a leap year in Sym454 (364 days), so day 365 must be rejected.
        assertThrows(DateTimeException.class,
                () -> Symmetry454Chronology.INSTANCE.dateYearDay(2000, 365));
    }
}
