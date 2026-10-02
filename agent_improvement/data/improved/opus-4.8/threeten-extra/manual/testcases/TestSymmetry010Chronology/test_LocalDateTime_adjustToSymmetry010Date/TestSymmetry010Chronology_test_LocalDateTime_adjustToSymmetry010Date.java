package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link LocalDateTime} with a {@link Symmetry010Date}
 * shifts the date part to the ISO-calendar equivalent of that Symmetry010 date,
 * while leaving the time-of-day untouched.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDateTime_adjustToSymmetry010Date {

    @Test
    public void test_LocalDateTime_adjustToSymmetry010Date() {
        // The Symmetry010 date 2012-07-19 corresponds to ISO date 2012-07-20.
        Symmetry010Date symmetry010Date = Symmetry010Date.of(2012, 7, 19);

        // Adjusting LocalDateTime.MIN keeps its time-of-day (00:00) and moves
        // the date to the ISO equivalent of the Symmetry010 date.
        LocalDateTime adjusted = LocalDateTime.MIN.with(symmetry010Date);

        assertEquals(LocalDateTime.of(2012, 7, 20, 0, 0), adjusted);
    }
}
