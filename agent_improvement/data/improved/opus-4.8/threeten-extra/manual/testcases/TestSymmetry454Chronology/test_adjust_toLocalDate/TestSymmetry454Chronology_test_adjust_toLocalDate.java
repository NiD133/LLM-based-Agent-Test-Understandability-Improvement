package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_adjust_toLocalDate {

    /**
     * Adjusting a Symmetry454 date with a LocalDate should yield the
     * Symmetry454 date that represents the same point in time.
     */
    @Test
    public void test_adjust_toLocalDate() {
        Symmetry454Date startDate = Symmetry454Date.of(2000, 1, 4);

        Symmetry454Date adjusted = startDate.with(LocalDate.of(2012, 7, 6));

        assertEquals(Symmetry454Date.of(2012, 7, 5), adjusted);
    }
}
