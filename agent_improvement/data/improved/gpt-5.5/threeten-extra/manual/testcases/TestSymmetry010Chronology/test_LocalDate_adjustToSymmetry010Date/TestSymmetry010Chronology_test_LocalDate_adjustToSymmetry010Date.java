package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDate_adjustToSymmetry010Date {

    @Test
    public void test_LocalDate_adjustToSymmetry010Date() {
        Symmetry010Date symmetryDate = Symmetry010Date.of(2012, 7, 19);

        LocalDate adjustedDate = LocalDate.MIN.with(symmetryDate);

        assertEquals(LocalDate.of(2012, 7, 20), adjustedDate);
    }
}
