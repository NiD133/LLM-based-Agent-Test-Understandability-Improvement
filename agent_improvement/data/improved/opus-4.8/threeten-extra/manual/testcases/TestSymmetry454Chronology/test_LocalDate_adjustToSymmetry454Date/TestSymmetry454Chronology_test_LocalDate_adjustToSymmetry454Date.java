package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_LocalDate_adjustToSymmetry454Date {

    /**
     * Adjusting a {@link LocalDate} with a {@link Symmetry454Date} should convert
     * the Symmetry454 date into the equivalent ISO {@link LocalDate}.
     * <p>
     * Symmetry454 date 2012-07-19 corresponds to ISO date 2012-07-20.
     */
    @Test
    public void test_LocalDate_adjustToSymmetry454Date() {
        Symmetry454Date symmetry454Date = Symmetry454Date.of(2012, 7, 19);
        LocalDate adjustedIsoDate = LocalDate.MIN.with(symmetry454Date);

        assertEquals(LocalDate.of(2012, 7, 20), adjustedIsoDate);
    }
}
