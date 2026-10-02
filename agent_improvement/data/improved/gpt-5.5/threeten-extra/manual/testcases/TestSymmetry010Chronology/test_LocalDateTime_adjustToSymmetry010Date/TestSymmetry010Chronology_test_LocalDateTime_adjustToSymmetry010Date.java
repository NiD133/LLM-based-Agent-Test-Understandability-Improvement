package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_LocalDateTime_adjustToSymmetry010Date {

    @Test
    public void test_LocalDateTime_adjustToSymmetry010Date() {
        Symmetry010Date sym010Date = Symmetry010Date.of(2012, 7, 19);

        LocalDateTime adjustedDateTime = LocalDateTime.MIN.with(sym010Date);

        assertEquals(LocalDateTime.of(2012, 7, 20, 0, 0), adjustedDateTime);
    }
}
