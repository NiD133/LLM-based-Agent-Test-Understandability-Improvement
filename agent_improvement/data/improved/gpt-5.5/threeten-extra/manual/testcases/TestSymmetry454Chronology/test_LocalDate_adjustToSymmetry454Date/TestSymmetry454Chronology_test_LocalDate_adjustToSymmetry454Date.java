package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_LocalDate_adjustToSymmetry454Date {

    @Test
    public void test_LocalDate_adjustToSymmetry454Date() {
        Symmetry454Date sym454 = Symmetry454Date.of(2012, 7, 19);

        LocalDate adjustedIsoDate = LocalDate.MIN.with(sym454);

        assertEquals(LocalDate.of(2012, 7, 20), adjustedIsoDate);
    }
}
