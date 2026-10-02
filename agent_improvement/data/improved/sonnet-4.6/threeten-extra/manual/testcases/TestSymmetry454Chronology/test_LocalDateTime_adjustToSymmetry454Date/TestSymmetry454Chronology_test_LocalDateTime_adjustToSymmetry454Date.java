package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_LocalDateTime_adjustToSymmetry454Date {

    /**
     * Verifies that adjusting a LocalDateTime with a Symmetry454Date replaces
     * the date portion using the ISO equivalent of that Symmetry454 date.
     *
     * Sym454 2012/07/19 maps to ISO 2012-07-20, so LocalDateTime.MIN.with(sym454)
     * should yield LocalDateTime.of(2012, 7, 20, 0, 0).
     */
    @Test
    public void test_LocalDateTime_adjustToSymmetry454Date() {
        Symmetry454Date sym454Date = Symmetry454Date.of(2012, 7, 19);
        LocalDateTime adjusted = LocalDateTime.MIN.with(sym454Date);
        assertEquals(LocalDateTime.of(2012, 7, 20, 0, 0), adjusted);
    }
}
