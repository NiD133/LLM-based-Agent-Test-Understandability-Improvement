package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDateTime_adjustToSymmetry010Date {

    /**
     * Verifies that applying a Symmetry010Date as a temporal adjuster to a LocalDateTime
     * replaces its date portion with the ISO-equivalent of that Symmetry010 date.
     *
     * Symmetry010Date 2012/07/19 maps to ISO 2012-07-20, so LocalDateTime.MIN adjusted
     * with that date should yield midnight on 2012-07-20.
     */
    @Test
    public void test_LocalDateTime_adjustToSymmetry010Date() {
        Symmetry010Date sym010 = Symmetry010Date.of(2012, 7, 19);
        LocalDateTime test = LocalDateTime.MIN.with(sym010);
        assertEquals(LocalDateTime.of(2012, 7, 20, 0, 0), test);
    }
}
