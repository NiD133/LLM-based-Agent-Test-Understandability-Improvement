package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        Symmetry454Date baseDate = Symmetry454Date.of(2000, 1, 4);
        LocalDate isoReplacementDate = LocalDate.of(2012, 7, 6);

        Symmetry454Date adjustedDate = baseDate.with(isoReplacementDate);

        assertEquals(Symmetry454Date.of(2012, 7, 5), adjustedDate);
    }
}
