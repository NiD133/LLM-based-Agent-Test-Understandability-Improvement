package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDate_adjustToSymmetry010Date {

    /**
     * Using a Symmetry010Date as a TemporalAdjuster should move a LocalDate to the
     * equivalent ISO calendar date. Symmetry010 2012-07-19 corresponds to ISO 2012-07-20.
     */
    @Test
    public void test_LocalDate_adjustToSymmetry010Date() {
        Symmetry010Date symmetryDate = Symmetry010Date.of(2012, 7, 19);

        LocalDate adjusted = LocalDate.MIN.with(symmetryDate);

        assertEquals(LocalDate.of(2012, 7, 20), adjusted);
    }
}
