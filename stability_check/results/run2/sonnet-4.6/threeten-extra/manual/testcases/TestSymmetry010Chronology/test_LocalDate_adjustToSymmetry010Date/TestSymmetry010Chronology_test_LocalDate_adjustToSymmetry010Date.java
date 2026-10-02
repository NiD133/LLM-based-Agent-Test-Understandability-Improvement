package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDate_adjustToSymmetry010Date {

    /**
     * Verifies that a Symmetry010Date can act as a TemporalAdjuster for a LocalDate.
     *
     * Symmetry010 date 2012-07-19 maps to ISO date 2012-07-20; calling LocalDate.with(sym010)
     * should yield that ISO equivalent regardless of the starting LocalDate.
     */
    @Test
    public void test_LocalDate_adjustToSymmetry010Date() {
        Symmetry010Date sym010Date = Symmetry010Date.of(2012, 7, 19);
        LocalDate result = LocalDate.MIN.with(sym010Date);
        assertEquals(LocalDate.of(2012, 7, 20), result);
    }
}
