package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link Symmetry454Date} can be used as a {@code TemporalAdjuster}
 * to set the date portion of a {@link LocalDateTime}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_LocalDateTime_adjustToSymmetry454Date {

    @Test
    public void test_LocalDateTime_adjustToSymmetry454Date() {
        // The Symmetry454 date 2012-07-19 corresponds to the ISO date 2012-07-20.
        Symmetry454Date symmetryDate = Symmetry454Date.of(2012, 7, 19);

        // Adjusting LocalDateTime.MIN with the Symmetry454 date replaces only the
        // date fields, leaving the time at midnight (00:00).
        LocalDateTime adjusted = LocalDateTime.MIN.with(symmetryDate);

        assertEquals(LocalDateTime.of(2012, 7, 20, 0, 0), adjusted);
    }
}
