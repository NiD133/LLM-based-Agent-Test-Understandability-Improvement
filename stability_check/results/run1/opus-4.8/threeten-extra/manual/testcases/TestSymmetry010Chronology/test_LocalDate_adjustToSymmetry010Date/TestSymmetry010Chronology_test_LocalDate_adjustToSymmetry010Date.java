package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests that an ISO {@link LocalDate} can be adjusted to match a
 * {@link Symmetry010Date} via {@link LocalDate#with(java.time.temporal.TemporalAdjuster)}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDate_adjustToSymmetry010Date {

    @Test
    public void test_LocalDate_adjustToSymmetry010Date() {
        // A Symmetry010 date used as the adjuster.
        Symmetry010Date symmetry010Date = Symmetry010Date.of(2012, 7, 19);

        // Adjusting any LocalDate with that Symmetry010 date yields the equivalent ISO date.
        LocalDate adjusted = LocalDate.MIN.with(symmetry010Date);

        assertEquals(LocalDate.of(2012, 7, 20), adjusted);
    }
}
