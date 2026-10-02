package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_LocalDateTime_adjustToSymmetry454Date {

    @Test
    public void test_LocalDateTime_adjustToSymmetry454Date() {
        Symmetry454Date symmetryDate = Symmetry454Date.of(2012, 7, 19);

        LocalDateTime adjustedDateTime = LocalDateTime.MIN.with(symmetryDate);

        assertEquals(LocalDateTime.of(2012, 7, 20, 0, 0), adjustedDateTime);
    }
}
