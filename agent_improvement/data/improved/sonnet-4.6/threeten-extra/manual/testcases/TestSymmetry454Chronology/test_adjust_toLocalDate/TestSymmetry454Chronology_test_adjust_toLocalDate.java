package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_adjust_toLocalDate {

    /**
     * Verifies that {@code with(LocalDate)} adjusts a Symmetry454Date to its calendar-equivalent
     * of a given ISO local date.  The initial Symmetry454Date is irrelevant to the result; only
     * the target ISO date drives the conversion.
     *
     * Concretely: ISO 2012-07-06 corresponds to Sym454 2012/07/05 (the Sym454 calendar is offset
     * by one day relative to ISO in that period).
     */
    @Test
    public void test_adjust_toLocalDate() {
        Symmetry454Date anyStartingDate = Symmetry454Date.of(2000, 1, 4);

        LocalDate targetIsoDate = LocalDate.of(2012, 7, 6);
        Symmetry454Date adjustedDate = anyStartingDate.with(targetIsoDate);

        Symmetry454Date expectedSym454Date = Symmetry454Date.of(2012, 7, 5);
        assertEquals(expectedSym454Date, adjustedDate);
    }
}
