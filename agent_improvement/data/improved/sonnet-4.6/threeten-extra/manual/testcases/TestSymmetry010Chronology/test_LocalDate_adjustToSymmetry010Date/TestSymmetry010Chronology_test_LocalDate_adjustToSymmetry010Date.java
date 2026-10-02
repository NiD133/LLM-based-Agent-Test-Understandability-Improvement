package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDate_adjustToSymmetry010Date {

    /**
     * Verifies that a Symmetry010Date can act as a TemporalAdjuster for a standard ISO LocalDate.
     * Calling LocalDate.with(symmetry010Date) converts the receiver to the ISO equivalent of
     * the Symmetry010 date, regardless of the receiver's original value.
     *
     * Symmetry010 2012/07/19 maps to ISO 2012-07-20.
     */
    @Test
    public void test_LocalDate_adjustToSymmetry010Date() {
        Symmetry010Date sym010Date = Symmetry010Date.of(2012, 7, 19);
        LocalDate isoResult = LocalDate.MIN.with(sym010Date);
        assertEquals(LocalDate.of(2012, 7, 20), isoResult);
    }
}
