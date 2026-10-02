package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_adjust_toLocalDate {

    /**
     * Verifies that adjusting a Symmetry010Date via an ISO LocalDate converts correctly.
     * ISO 2012-07-06 maps to Sym010 2012/07/05 because the Sym010 calendar is one day
     * ahead of ISO on that date (the calendars are not perfectly aligned).
     */
    @Test
    public void test_adjust_toLocalDate() {
        Symmetry010Date sym010 = Symmetry010Date.of(2000, 1, 4);
        Symmetry010Date result = sym010.with(LocalDate.of(2012, 7, 6));
        assertEquals(Symmetry010Date.of(2012, 7, 5), result);
    }
}
